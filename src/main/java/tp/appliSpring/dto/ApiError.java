package tp.appliSpring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

//@Getter @Setter @ToString @NoArgsConstructor
public class ApiError {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    private String message;
    //private String details;

    public ApiError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }


    public ApiError() {
        super();
    }


    public HttpStatus getStatus() {
        return status;
    }


    public void setStatus(HttpStatus status) {
        this.status = status;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "ApiError [status=" + status + ", timestamp=" + timestamp + ", message=" + message + "]";
    }


}
