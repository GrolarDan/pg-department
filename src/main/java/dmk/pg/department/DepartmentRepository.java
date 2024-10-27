package dmk.pg.department;

public interface DepartmentRepository {
  DepartmentDTO findById(Long id);
  boolean save(DepartmentDTO department);
}
