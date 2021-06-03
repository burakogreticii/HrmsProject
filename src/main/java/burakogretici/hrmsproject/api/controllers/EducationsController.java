package burakogretici.hrmsproject.api.controllers;

import burakogretici.hrmsproject.business.abstracts.EducationService;
import burakogretici.hrmsproject.business.conctants.Messages;
import burakogretici.hrmsproject.core.utilities.results.DataResult;
import burakogretici.hrmsproject.core.utilities.results.ErrorDataResult;
import burakogretici.hrmsproject.core.utilities.results.Result;
import burakogretici.hrmsproject.entities.concretes.Education;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cvs/educations")
public class EducationsController {

    private EducationService educationService;

    @Autowired
    public EducationsController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping("/add")
    public ResponseEntity<Result> add(@RequestBody @Valid Education education) {
        Result result = educationService.add(education);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/getall")
    public ResponseEntity<DataResult<List<Education>>> getAll() {

        DataResult<List<Education>> result = educationService.getAll();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/getall/bycvid")
    public ResponseEntity<DataResult<List<Education>>> getAllByJobSeekerCv_Id(int cvId) {
        DataResult<List<Education>> result = educationService.getAllByCv_Id(cvId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/getall/bycvidorderbyendyear")
    public ResponseEntity<DataResult<List<Education>>> getAllByCv_IdOrderByEndYear(@RequestParam int cvId, @RequestParam String direction) {
        DataResult<List<Education>> result = educationService.getAllByCv_IdOrderByEndYear(cvId,direction);

        return ResponseEntity.ok(result);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationException
            (MethodArgumentNotValidException exceptions) {
        Map<String, String> validationErrors = new HashMap<String, String>();
        for (FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors, Messages.verificationErrors);
        return errors;
    }
}
