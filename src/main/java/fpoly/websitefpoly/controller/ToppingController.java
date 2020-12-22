package fpoly.websitefpoly.controller;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.dto.ToppingDto;
import fpoly.websitefpoly.entity.Topping;
import fpoly.websitefpoly.request.CreateToppingRequest;
import fpoly.websitefpoly.request.UpdateToppingRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.ToppingService;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/topping")
public class ToppingController {

    private final ToppingService toppingService;

    public ToppingController(ToppingService toppingService) {
        this.toppingService = toppingService;
    }

    @GetMapping("/")
    public ResponeData<List<Topping>> search() {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, toppingService.getAll());
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE);
        }
    }

    @GetMapping(value = {"/", ""})
    public ResponeData<Page<Topping>> getAll(@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        try {
            return toppingService.search(pageable);
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }

    @GetMapping("/{name}")
    public ResponeData<Page<Topping>> findByName(@PathVariable("name") String name, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        try {
            return toppingService.findByName(name, pageable);
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }

//    @GetMapping("/{id}")
//    public ResponeData<ToppingDto> detail(@PathVariable("id")Long id){
//        try {
//            return toppingService.detail(id);
//        }catch (Exception e){
//            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
//        }
//    }

    @PostMapping(value = "/")
    public ResponeData<ToppingDto> create(@RequestBody CreateToppingRequest createToppingRequest) {
        try {
            return toppingService.created(createToppingRequest);
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.toString());
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_CODE);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponeData<ToppingDto> update(@PathVariable("id") Long id, @RequestBody UpdateToppingRequest updateToppingRequest) {
        try {

            return toppingService.updated(id, updateToppingRequest);
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.toString());
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_CODE);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponeData<Boolean> delete(@PathVariable("id") Long id) {
        try {
            return toppingService.deleted(id);
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.toString());
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_CODE);
        }
    }

}
