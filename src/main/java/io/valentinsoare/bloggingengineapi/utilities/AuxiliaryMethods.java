package io.valentinsoare.bloggingengineapi.utilities;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class AuxiliaryMethods {
    private static AuxiliaryMethods instance;

    public static AuxiliaryMethods getInstance() {
        if (instance == null) {
            instance = new AuxiliaryMethods();
        }

        return instance;
    }

    public Pageable sortingWithDirections(String sortDir, String sortBy, int pageNo, int pageSize) {
        Pageable pageable;

        if (Sort.Direction.ASC.name().equalsIgnoreCase(sortDir)) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        return pageable;
    }

    public <T> T updateIfPresent(T newValue, T currentValue) {
        if (newValue instanceof String) {
            return !((String) newValue).isBlank() ? newValue : currentValue;
        }

        return newValue != null ? newValue : currentValue;
    }
}
