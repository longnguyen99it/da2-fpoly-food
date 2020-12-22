package fpoly.websitefpoly.service;

import fpoly.websitefpoly.entity.Topping;
import fpoly.websitefpoly.response.ResponeData;
import org.springframework.data.domain.Page;

import java.util.List;


import fpoly.websitefpoly.dto.ToppingDto;
import fpoly.websitefpoly.entity.Topping;
import fpoly.websitefpoly.request.*;
import fpoly.websitefpoly.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToppingService {

    List<Topping> getAll();

    ResponeData<Page<Topping>> search(Pageable pageable) throws Exception;
    ResponeData<Page<Topping>> findByName(String name,Pageable pageable) throws Exception;

    ResponeData<ToppingDto> created(CreateToppingRequest createToppingRequest) throws Exception;

    ResponeData<ToppingDto> updated(Long id, UpdateToppingRequest updateToppingRequest) throws Exception;

    ResponeData<ToppingDto> detail(Long id) throws Exception;

    ResponeData<Boolean> deleted(Long id) throws Exception;

}
