package tp.appliSpring.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.dto.CompteDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MyMapper {

    MyMapper INSTANCE = Mappers.getMapper(MyMapper.class);


    Compte compteDtoToCompte(CompteDto source);

    CompteDto compteToCompteDto(Compte source);

    List<CompteDto> compteListToCompteDtoList(List<Compte> source);
}
