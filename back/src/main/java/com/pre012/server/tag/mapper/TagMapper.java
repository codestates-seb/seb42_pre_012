package com.pre012.server.tag.mapper;

import com.pre012.server.tag.dto.TagDto;
import com.pre012.server.tag.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag tagRequestDtoToTag(TagDto.Request requestBody);

    List<Tag> tagRequestDtosToTags(List<TagDto.Request> requests);

}
