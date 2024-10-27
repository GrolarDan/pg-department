package dmk.pg.department;

import java.util.ArrayList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * <pre>
 * 1. Only direct manager or hierarchical manager could move the department
 * 2. Root department could not be moved. Must stay root. Child department could not be moved to be root.
 * 3. Department could be moved only under department where manager is direct or hierarchical owner of that parent department.
 * </pre>
 */
@RequiredArgsConstructor
public class DepartmentService {
  private final DepartmentRepository departmentRepository;

  /**
   * Moves the department with <code>departmentId</code> under department with id of <code>parentId</code>.
   * Returns <code>TRUE</code> if it is possible to move and saving was successful. Otherwise, returns <code>FALSE</code>.
   *
   * @param departmentId Department id of moving department
   * @param parentId Parent department id where the department is moving
   * @param managerId Manager id who is applying the movement.
   * @return <code>TRUE</code> when it is possible to move and moving was successful. Otherwise, <code>FALSE</code>.
   */
  public boolean moveDepartment(Long departmentId, Long parentId, @NonNull Long managerId) {
    // try to set department as root (2.condition - second)
    if (parentId == null) {
      return false;
    }

    DepartmentDTO department = departmentRepository.findById(departmentId);

    // not existing department or try to move root department (2. condition - first part)
    if (department == null || department.getParentId() == null) {
      return false;
    }

    DepartmentDTO parentDepartment = departmentRepository.findById(parentId);

    // 1. condition check
    if (!isDepartmentManager(department, managerId)) {
      return false;
    }
    // 3. condition check
    if (!isDepartmentManager(parentDepartment, managerId)) {
      return false;
    }

    // TODO prevent cycle move

    department.setParentId(parentId);
    return departmentRepository.save(department);
  }

  private boolean isDepartmentManager(DepartmentDTO department, Long managerId) {
    var tempDepartment = department;
    var visitedDepartments = new ArrayList<Long>();

    while (!managerId.equals(tempDepartment.getManagerId()) && tempDepartment.getParentId() != null && !visitedDepartments.contains(tempDepartment.getId())) {
      visitedDepartments.add(tempDepartment.getId());
      tempDepartment = departmentRepository.findById(tempDepartment.getParentId());
    }

    return managerId.equals(tempDepartment.getManagerId());
  }
}
