package uz.pdp.task1.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.task1.entity.Attachment;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.service.AttachmentService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @GetMapping
    public ResponseEntity<List<Attachment>> getAttachments(){
        return ResponseEntity.ok(attachmentService.getAttachments());
    }

    @GetMapping("/{id}")
    public void getAttachment(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        attachmentService.getAttachment(id, response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addAttachment(MultipartHttpServletRequest request) throws IOException {
        ApiResponse apiResponse = attachmentService.uploadFile(request);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAttachment(@PathVariable Integer id){
        ApiResponse apiResponse = attachmentService.deleteFile(id);
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
