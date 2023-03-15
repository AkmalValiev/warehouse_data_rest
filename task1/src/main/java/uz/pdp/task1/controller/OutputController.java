package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Output;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.OutputDto;
import uz.pdp.task1.service.OutputService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/output")
public class OutputController {

    @Autowired
    OutputService outputService;

    @GetMapping
    public ResponseEntity<List<Output>> geOutputs(){
        return ResponseEntity.ok(outputService.getOutputs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Output> getOutput(@PathVariable Integer id){
        return ResponseEntity.ok(outputService.getOutput(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addOutput(@RequestBody OutputDto outputDto){
        ApiResponse apiResponse = outputService.addOutput(outputDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editOutput(@PathVariable Integer id, @RequestBody OutputDto outputDto){
        ApiResponse apiResponse = outputService.editOutput(id, outputDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOutput(@PathVariable Integer id){
        ApiResponse apiResponse = outputService.deleteOutput(id);
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
