package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.User;
import uz.pdp.task1.entity.Warehouse;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.UserDto;
import uz.pdp.task1.repository.UserRepository;
import uz.pdp.task1.repository.WarehouseRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

   @Autowired
    UserRepository userRepository;
   @Autowired
    WarehouseRepository warehouseRepository;

   public List<User> getUsers(){
       return userRepository.findAll();
   }

   public User getUser(Integer id){
       Optional<User> optionalUser = userRepository.findById(id);
       return optionalUser.orElseGet(User::new);
   }

   public ApiResponse addUser(UserDto userDto){
       boolean exists = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
       if (exists)
           return new ApiResponse("Kiritilgan phone number mavjud!", false);

       List<User> userList = userRepository.findAll();
       int code=0;
       if (!userList.isEmpty()){
           code = Integer.parseInt(userList.get(userList.size()-1).getCode())+1;
       }else {
           code = 1;
       }
       Set<Warehouse> warehouses = new HashSet<>();
       Set<Integer> warehousesId = userDto.getWarehousesId();
       for (Integer integer : warehousesId) {
           Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(integer);
           if (optionalWarehouse.isPresent()){
               warehouses.add(optionalWarehouse.get());
           }else {
               return new ApiResponse("Kiritilgan id li warehouse topilmadi!", false);
           }
       }

       User user = new User();
       user.setActive(userDto.isActive());
       user.setCode(String.valueOf(code));
       user.setPassword(userDto.getPassword());
       user.setWarehouses(warehouses);
       user.setFirstName(userDto.getFirstName());
       user.setLastName(userDto.getLastName());
       user.setPhoneNumber(userDto.getPhoneNumber());
       userRepository.save(user);
       return new ApiResponse("User qo'shildi!", true);
   }

   public ApiResponse editUser(Integer id, UserDto userDto){
       Optional<User> optionalUser = userRepository.findById(id);
       if (!optionalUser.isPresent())
           return new ApiResponse("Kiritilgan id li user topilmadi!", false);
       User user = optionalUser.get();

       boolean exists = userRepository.existsByPhoneNumberAndIdNot(userDto.getPhoneNumber(), id);
       if (exists)
           return new ApiResponse("Kiritilgan phone number mavjud!", false);
       Set<Warehouse> warehouses = new HashSet<>();
       Set<Integer> warehousesId = userDto.getWarehousesId();
       for (Integer integer : warehousesId) {
           Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(integer);
           if (optionalWarehouse.isPresent()){
               warehouses.add(optionalWarehouse.get());
           }else {
               return new ApiResponse("Kiritilgan id li warehouse topilmadi!", false);
           }
       }

       user.setActive(userDto.isActive());
       user.setPassword(userDto.getPassword());
       user.setWarehouses(warehouses);
       user.setFirstName(userDto.getFirstName());
       user.setLastName(userDto.getLastName());
       user.setPhoneNumber(userDto.getPhoneNumber());
       userRepository.save(user);
       return new ApiResponse("User taxrirlandi!", true);

   }

   public ApiResponse deleteUser(Integer id){
       try {
           userRepository.deleteById(id);
           return new ApiResponse("User o'chirildi!", true);
       }catch (Exception e){
           return new ApiResponse("Xatolik!!!", false);
       }
   }

}
