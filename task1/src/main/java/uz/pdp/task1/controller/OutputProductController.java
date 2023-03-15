package uz.pdp.task1.controller;

import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.OutputProduct;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.OutputProductDto;
import uz.pdp.task1.service.OutputProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/outputProduct")
public class OutputProductController {

    @Autowired
    OutputProductService outputProductService;

    @GetMapping
    public ResponseEntity<List<OutputProduct>> fetOutputProducts(){
        return ResponseEntity.ok(outputProductService.getOutputProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutputProduct> getOutputProduct(@PathVariable Integer id){
        return ResponseEntity.ok(outputProductService.getOutputProduct(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addOutputProduct(@RequestBody OutputProductDto outputProductDto){
        ApiResponse apiResponse = outputProductService.addOutputProduct(outputProductDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editOutputProduct(@PathVariable Integer id, @RequestBody OutputProductDto outputProductDto){
        ApiResponse apiResponse = outputProductService.editOutputProduct(id, outputProductDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOutputProduct(@PathVariable Integer id){
        ApiResponse apiResponse = outputProductService.deleteOutputProduct(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
