package app_checking.util.mapper;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@NoRepositoryBean
public abstract class BaseMapper <Source, Target>{

    protected final BoundMapperFacade<Source, Target> mapper;

    private Class<Source> sourceType;

    private Class<Target> targetType;

    public BaseMapper(MapperFactory mapperFactory, Class<Source> sourceType, Class<Target> targetType){
        super();
        this.sourceType = sourceType;
        this.targetType = targetType;

        mapper = registerMapper(mapperFactory);
    }

    protected BoundMapperFacade<Source, Target> registerMapper(MapperFactory mapperFactory){
        mapperFactory.classMap(sourceType,targetType)
                .byDefault()
                .register();
        return mapperFactory.getMapperFacade(sourceType, targetType);
    }

    public Target map(Source source){
        return mapper.map(source);
    }
    
    public List<Target> mapList(List<Source> source){
    	if(null == source){
			return null;
		}
		else {
			return source.stream()
					.map(mapper::map)
					.collect(Collectors.toList());
		}
    }

    public Target map(Source source, Target target){
        return mapper.map(source, target);
    }
}
