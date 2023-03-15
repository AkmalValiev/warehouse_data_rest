package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Currency;
import uz.pdp.task1.entity.Input;
import uz.pdp.task1.entity.Supplier;
import uz.pdp.task1.entity.Warehouse;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.InputDto;
import uz.pdp.task1.repository.CurrencyRepository;
import uz.pdp.task1.repository.InputRepository;
import uz.pdp.task1.repository.SupplierRepository;
import uz.pdp.task1.repository.WarehouseRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class InputService {

    @Autowired
    InputRepository inputRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public List<Input> getInputs(){
        return inputRepository.findAll();
    }

    public Input getInput(Integer id){
        Optional<Input> optionalInput = inputRepository.findById(id);
        return optionalInput.orElseGet(Input::new);
    }

    public ApiResponse addInput(InputDto inputDto){
        Timestamp timestamp = Timestamp.from(Instant.now());

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha warehouse topilmadi!", false);
        Warehouse warehouse = optionalWarehouse.get();

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha supplier topilmadi!", false);
        Supplier supplier = optionalSupplier.get();

        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha currency topilmadi!", false);
        Currency currency = optionalCurrency.get();

        int code = 0;
        List<Input> inputList = inputRepository.findAll();
        if (!inputList.isEmpty()){
            code = Integer.parseInt(inputList.get(inputList.size()-1).getCode())+1;
        }else {
            code = 1;
        }

        Input input = new Input();
        input.setCode(String.valueOf(code));
        input.setDate(timestamp);
        input.setCurrency(currency);
        input.setSupplier(supplier);
        input.setWarehouse(warehouse);
        input.setFactureNumber(inputDto.getFactureNumber());
        inputRepository.save(input);
        return new ApiResponse("Input qo'shildi!", true);

    }

    public ApiResponse editInput(Integer id, InputDto inputDto){
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (!optionalInput.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha input topilmadi!", false);
        Input input = optionalInput.get();

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha warehouse topilmadi!", false);
        Warehouse warehouse = optionalWarehouse.get();

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha supplier topilmadi!", false);
        Supplier supplier = optionalSupplier.get();

        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha currency topilmadi!", false);
        Currency currency = optionalCurrency.get();

        input.setCurrency(currency);
        input.setSupplier(supplier);
        input.setWarehouse(warehouse);
        input.setFactureNumber(inputDto.getFactureNumber());
        inputRepository.save(input);
        return new ApiResponse("Input taxrirlandi!", true);
    }

    public ApiResponse deleteInput(Integer id){
        try {
            inputRepository.deleteById(id);
            return new ApiResponse("Input o'chirildi!", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }
    }

}
