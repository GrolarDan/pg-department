package dmk.pg.department;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * <pre>
 *                 ------ root (M0) ------
 *               /                        \
 *         --- L1 (M1) ---              -- R1 (M2) --
 *        /               \            /             \
 *    - L1L1 (null) -    L1R1 (M2)   R1L1 (M3)      R1R1 (M1)
 *   /               \
 * L1L1L1 (M3)     L1L1R1 (M4)
 * </pre>
 */
@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
  private final Long M0_ID = 0L;
  private final Long M1_ID = 1L;
  private final Long M2_ID = 2L;
  private final Long M3_ID = 3L;
  private final Long M4_ID = 4L;
  private final Long DROOT_ID = 0L;
  private final Long DL1_ID = 1L;
  private final Long DL1L1_ID = 2L;
  private final Long DL1L1L1_ID = 3L;
  private final Long DL1L1R1_ID = 4L;
  private final Long DL1R1_ID = 5L;
  private final Long DR1_ID = 6L;
  private final Long DR1L1_ID = 7L;
  private final Long DR1R1_ID = 8L;

  private final DepartmentDTO root = createDepartment(DROOT_ID, null, M0_ID);
  private final DepartmentDTO L1 = createDepartment(DL1_ID, DROOT_ID, M1_ID);
  private final DepartmentDTO L1L1 = createDepartment(DL1L1_ID, DL1_ID, null);
  private final DepartmentDTO L1L1L1 = createDepartment(DL1L1L1_ID, DL1L1_ID, M3_ID);
  private final DepartmentDTO L1L1R1 = createDepartment(DL1L1R1_ID, DL1L1_ID, M4_ID);
  private final DepartmentDTO L1R1 = createDepartment(DL1R1_ID, DL1_ID, M2_ID);
  private final DepartmentDTO R1 = createDepartment(DR1_ID, DROOT_ID, M2_ID);
  private final DepartmentDTO R1L1 = createDepartment(DR1L1_ID, DR1_ID, M3_ID);
  private final DepartmentDTO R1R1 = createDepartment(DR1R1_ID, DR1_ID, M1_ID);

  @Mock
  private DepartmentRepository departmentRepository;

  @InjectMocks
  private DepartmentService departmentService;

  // Check 2. condition - first part (child department could not be moved to be root)
  @Test
  void moveDepartment_childDepartmentToRoot() {
    var result = departmentService.moveDepartment(DL1L1R1_ID, null, M0_ID);

    assertFalse(result);
  }

  // Check 2. condition - second part (root department could not be moved. Must stay root)

  // Check 1. condition (Only direct manager or hierarchical manager could move the department) - direct manager

  // Check 1. condition (Only direct manager or hierarchical manager could move the department) - hierarchical manager

  // Check 1. condition (Only direct manager or hierarchical manager could move the department) - not direct or hierarchical manager

  // Check 3. condition (Department could be moved only under department where manager is direct or hierarchical owner of that parent department) - direct manager

  // Check 3. condition (Department could be moved only under department where manager is direct or hierarchical owner of that parent department) - hierarchical manager

  // Check 3. condition (Department could be moved only under department where manager is direct or hierarchical owner of that parent department) - not direct or hierarchical manager

  // Check cycle move prevention

  private DepartmentDTO createDepartment(Long id, Long parentId, Long managerId) {
    var department = new DepartmentDTO();
    department.setId(id);
    department.setParentId(parentId);
    department.setManagerId(managerId);
    return department;
  }
}