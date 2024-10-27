package dmk.pg.department;

import lombok.Data;

@Data
public class DepartmentDTO {
  private Long id;
  private String name;
  private Long parentId;
  private Long managerId;
}
