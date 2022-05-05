package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class Dto {
    String name;
    FILE_TYPE type;
    Map<String,Byte>content;
    String path;
}
