package fpoly.websitefpoly.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nguyen Hoang Long on 9/9/2020
 * @created 9/9/2020
 * @project demo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponeData<T> {

    private String code;
    private String description;
    private T body;

    public ResponeData(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public ResponeData(T iSaving) {
        this.body = iSaving;
    }
}