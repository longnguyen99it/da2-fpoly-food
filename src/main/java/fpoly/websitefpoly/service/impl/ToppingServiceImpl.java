package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.entity.Topping;
import fpoly.websitefpoly.repository.ToppingRepository;
import fpoly.websitefpoly.service.ToppingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToppingServiceImpl implements ToppingService {

    private final ToppingRepository toppingRepository;

    public ToppingServiceImpl(ToppingRepository toppingRepository) {
        this.toppingRepository = toppingRepository;
    }

    @Override
    public List<Topping> getAll() {
        return toppingRepository.findAll();
    }
}
