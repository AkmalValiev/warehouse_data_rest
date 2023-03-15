package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Input;
import uz.pdp.task1.entity.InputProduct;
import uz.pdp.task1.entity.Product;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.InputProductDto;
import uz.pdp.task1.repository.InputProductRepository;
import uz.pdp.task1.repository.InputRepository;
import uz.pdp.task1.repository.ProductRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InputProductService {

    @Autowired
    InputProductRepository inputProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InputRepository inputRepository;

    public List<InputProduct> getInputProducts(){
        return inputProductRepository.findAll();
    }

    public InputProduct getInputProduct(Integer id){
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        return optionalInputProduct.orElseGet(InputProduct::new);
    }

    public ApiResponse addInputProduct(InputProductDto inputProductDto){
        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha product topilmadi!", false);
        Product product = optionalProduct.get();

        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha input topilmadi!", false);
        Input input = optionalInput.get();

        Date date = new Date();

        InputProduct inputProduct = new InputProduct();
        inputProduct.setProduct(product);
        inputProduct.setInput(input);
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setExpireDate(date);
        inputProductRepository.save(inputProduct);
        return new ApiResponse("InputProduct qo'shildi!", true);
    }

    public ApiResponse editInputProduct(Integer id, InputProductDto inputProductDto){
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        if (!optionalInputProduct.isPresent())
            return new ApiResponse("Kiritilgan id li inputProduct topilmadi!", false);
        InputProduct inputProduct = optionalInputProduct.get();

        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha product topilmadi!", false);
        Product product = optionalProduct.get();

        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha input topilmadi!", false);
        Input input = optionalInput.get();

        inputProduct.setProduct(product);
        inputProduct.setInput(input);
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProductRepository.save(inputProduct);
        return new ApiResponse("InputProduct taxrirlandi!", true);

    }

    public ApiResponse deleteInputProduct(Integer id){
        try {
            inputProductRepository.deleteById(id);
            return new ApiResponse("InputProduct o'chirildi!", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }
    }

}
