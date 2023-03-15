package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.InputProduct;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.InputProductDto;
import uz.pdp.task1.service.InputProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inputProduct")
public class InputProductController {

    @Autowired
    InputProductService inputProductService;

    @GetMapping
    public ResponseEntity<List<InputProduct>> getInputProducts(){
        return ResponseEntity.ok(inputProductService.getInputProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputProduct> getInputProduct(@PathVariable Integer id){
        return ResponseEntity.ok(inputProductService.getInputProduct(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addInputProduct(@RequestBody InputProductDto inputProductDto){
        ApiResponse apiResponse = inputProductService.addInputProduct(inputProductDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editInputProduct(@PathVariable Integer id, @RequestBody InputProductDto inputProductDto){
        ApiResponse apiResponse = inputProductService.editInputProduct(id, inputProductDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteInputProduct(@PathVariable Integer id){
        ApiResponse apiResponse = inputProductService.deleteInputProduct(id);
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
