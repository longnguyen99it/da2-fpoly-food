package fpoly.websitefpoly.common;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nguyen Hoang Long on 9/9/2020
 * @created 9/9/2020
 * @project demo
 */
public class ModelMapperUtils {
    private static ModelMapper modelMapper = new ModelMapper();
    //
    public static <D,E> D map(final E entity,final Class<D> dtoClass){
        return modelMapper.map(entity,dtoClass);
    }

    public static <D,E> List<D> mapAll(final Collection<E> entityList, final Class<D> dtoClass){
        return entityList.stream().map(entity -> modelMapper.map(entity,dtoClass)).collect(Collectors.toList());
    }
}
