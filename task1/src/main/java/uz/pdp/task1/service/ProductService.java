package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Attachment;
import uz.pdp.task1.entity.Category;
import uz.pdp.task1.entity.Measurement;
import uz.pdp.task1.entity.Product;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.ProductDto;
import uz.pdp.task1.repository.AttachmentRepository;
import uz.pdp.task1.repository.CategoryRepository;
import uz.pdp.task1.repository.MeasurementRepository;
import uz.pdp.task1.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    MeasurementRepository measurementRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElseGet(Product::new);
    }

    public ApiResponse addProduct(ProductDto productDto){
        boolean exists = productRepository.existsByName(productDto.getName());
        if (exists)
            return new ApiResponse("Bunaqa name li product mavjud!", false);

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new ApiResponse("Kiritilgan id li category topilmadi!", false);
        Category category = optionalCategory.get();

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("Kiritilgan id li attachment topilmadi!", false);
        Attachment attachment = optionalAttachment.get();

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new ApiResponse("Kiritilgan id li measurement topilmadi!", false);
        Measurement measurement = optionalMeasurement.get();

        int code = 0;
        List<Product> productList = productRepository.findAll();
        if (!productList.isEmpty()){
            code = Integer.parseInt(productList.get(productList.size()-1).getCode())+1;
        }else {
            code = 1;
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setCode(String.valueOf(code));
        product.setFile(attachment);
        product.setCategory(category);
        product.setActive(productDto.isActive());
        product.setMeasurement(measurement);
        productRepository.save(product);
        return new ApiResponse("Product qo'shildi!", true);
    }

    public ApiResponse editProduct(Integer id, ProductDto productDto){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            return new ApiResponse("Kiritilgan id li product topilmadi!", false);
        Product product = optionalProduct.get();

        boolean exists = productRepository.existsByNameAndIdNot(productDto.getName(), id);
        if (exists)
            return new ApiResponse("Bunaqa name li product mavjud!", false);

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new ApiResponse("Kiritilgan id li category topilmadi!", false);
        Category category = optionalCategory.get();

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("Kiritilgan id li attachment topilmadi!", false);
        Attachment attachment = optionalAttachment.get();

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new ApiResponse("Kiritilgan id li measurement topilmadi!", false);
        Measurement measurement = optionalMeasurement.get();

        product.setName(productDto.getName());
        product.setFile(attachment);
        product.setCategory(category);
        product.setActive(productDto.isActive());
        product.setMeasurement(measurement);
        productRepository.save(product);
        return new ApiResponse("Product taxrirlandi!", true);
    }

    public ApiResponse deleteProduct(Integer id){
        try {
            productRepository.deleteById(id);
            return new ApiResponse("Product o'chirildi!", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }
    }

}
