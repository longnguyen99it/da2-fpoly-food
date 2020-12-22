package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.dto.ToppingDto;
import fpoly.websitefpoly.entity.Topping;
import fpoly.websitefpoly.repository.ToppingRepository;
import fpoly.websitefpoly.request.CreateToppingRequest;
import fpoly.websitefpoly.request.UpdateToppingRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.ToppingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ToppingServiceImpl implements ToppingService {

    private final ToppingRepository toppingRepository;

    public ToppingServiceImpl(ToppingRepository toppingRepository) {
        this.toppingRepository = toppingRepository;
    }

    @Override
    public List<Topping> getAll() {
        return toppingRepository.findAllByStatus("A");
    }

    @Override
    public ResponeData<Page<Topping>> search(Pageable pageable) throws Exception {
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE,AppConstant.SUCCESSFUL_MESAGE,toppingRepository.findAll(pageable));
    }

    @Override
    public ResponeData<Page<Topping>> findByName(String name,Pageable pageable) throws Exception {
        if(name != null){
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE,AppConstant.SUCCESSFUL_MESAGE,toppingRepository.findByNameContaining(name,pageable));}
        else {
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE,AppConstant.FILE_NOT_FOUND_MESSAGE);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponeData<ToppingDto> created(CreateToppingRequest createToppingRequest) throws Exception {
        Topping topping = new Topping();

        topping.setName(createToppingRequest.getNameTopping());
        topping.setPrice(createToppingRequest.getPriceTopping());
        topping.setStatus("A");
        Topping storedTopping = toppingRepository.save(topping);

        ToppingDto resultValue = new ToppingDto();
        resultValue.setNameTopping(storedTopping.getName());
        resultValue.setPriceTopping(storedTopping.getPrice());
        if(resultValue !=null){
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE,AppConstant.SUCCESSFUL_MESAGE,resultValue);
        }else {
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE,AppConstant.FILE_NOT_FOUND_MESSAGE);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponeData<ToppingDto> updated(Long id, UpdateToppingRequest updateToppingRequest) throws Exception {
        ToppingDto resultValue = new ToppingDto();

        Topping topping = toppingRepository.findById(id).get();
        topping.setName(updateToppingRequest.getNameTopping());
        topping.setPrice(updateToppingRequest.getPriceTopping());
        topping.setStatus(updateToppingRequest.getStatus());
//        BeanUtils.copyProperties(updateToppingRequest, topping);

        Topping updateTopping = toppingRepository.save(topping);
        resultValue.setNameTopping(updateTopping.getName());
        resultValue.setPriceTopping(updateTopping.getPrice());
//        BeanUtils.copyProperties(resultValue,updateTopping);

        if(resultValue != null){
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE,AppConstant.SUCCESSFUL_MESAGE,resultValue);
        }else {
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE,AppConstant.FILE_NOT_FOUND_MESSAGE,null);
        }
    }


    @Override
    public ResponeData<ToppingDto> detail(Long id) throws Exception {
        ToppingDto resultValue = new ToppingDto();
        if(id !=null){
            Topping topping = toppingRepository.findById(id).get();
            BeanUtils.copyProperties(topping,resultValue);
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE,AppConstant.SUCCESSFUL_MESAGE,resultValue);
        }else {
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE,AppConstant.FILE_NOT_FOUND_MESSAGE,null);
        }

    }

    @Override
    public ResponeData<Boolean> deleted(Long id) throws Exception {
        Topping topping = toppingRepository.findByIdAndStatus(id,"A");
        if(topping != null ){
            topping.setStatus("D");
            toppingRepository.save(topping);
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE,AppConstant.SUCCESSFUL_MESAGE,true);
        }else {
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE,AppConstant.FILE_NOT_FOUND_MESSAGE,false);
        }

    }

}
