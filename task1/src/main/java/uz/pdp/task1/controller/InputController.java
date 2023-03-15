package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Input;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.InputDto;
import uz.pdp.task1.service.InputService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/input")
public class InputController {

    @Autowired
    InputService inputService;

    @GetMapping
    public ResponseEntity<List<Input>> getInputs(){
        return ResponseEntity.ok(inputService.getInputs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Input> getInput(@PathVariable Integer id){
        return ResponseEntity.ok(inputService.getInput(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addInput(@RequestBody InputDto inputDto){
        ApiResponse apiResponse = inputService.addInput(inputDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editInput(@PathVariable Integer id, @RequestBody InputDto inputDto){
        ApiResponse apiResponse = inputService.editInput(id, inputDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteINput(@PathVariable Integer id){
        ApiResponse apiResponse = inputService.deleteInput(id);
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
