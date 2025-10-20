package sv.edu.catolica.unirutas.data.remote;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String error;

    public ApiResponse(boolean success, T data, String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    // Getters y Setters
    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public String getError() { return error; }

    // Métodos estáticos para crear respuestas
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(false, null, error);
    }
}