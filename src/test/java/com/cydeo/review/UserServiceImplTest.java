package com.cydeo.review;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import com.cydeo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {


    @Mock
    private  UserRepository userRepository;

    @Mock
    private  UserMapper userMapper;

    @Mock
    private ProjectService projectService;

    @Mock
    private  TaskService taskService;

    @Mock
    private KeycloakService keycloakService;

    @InjectMocks
    private UserServiceImpl userService;


    User user;
    UserDTO userDTO;





//    @BeforeAll
//    public static void setUpBeforeClass() throws Exception{
//        // Some impl
//    }
//
//
//    @AfterAll
//    public static void tearDownAfterClass() throws Exception{
//        // Some impl
//    }
//


    @BeforeEach
    public void setUp() throws Exception{
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("user");
        user.setPassWord("Abc1");
        user.setEnabled(true);
        user.setRole(new Role("Manager"));


        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setUserName("user");
        userDTO.setPassWord("Abc1");
        userDTO.setEnabled(true);

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setDescription("Manager");

        userDTO.setRole(roleDTO);


    }

    private List<User> getUsers(){
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Emily");

        return List.of(user, user2);
    }

    private List<UserDTO> getUserDTOs(){
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(2L);
        userDTO2.setFirstName("Emily");

        return List.of(userDTO, userDTO2);
    }

    @AfterEach
    public void tearDown() throws Exception{
        // Some impl
    }


    // TestNG
    // Test Engine

    @Test
    public void test(){

    }

    @Test
    public void should_list_all_users(){

        // given - Preparation
        when(userRepository.findAllByIsDeletedOrderByFirstNameDesc(false)).thenReturn(getUsers());
        when(userMapper.convertToDto(user)).thenReturn(userDTO);
        when(userMapper.convertToDto(getUsers().get(1))).thenReturn(getUserDTOs().get(1));

//        when(userMapper.convertToDto(any(User.class))).thenReturn(userDTO, getUserDTOs().get(1));

        List<UserDTO> expectedList = getUserDTOs();

        // when - Action
      List<UserDTO> actualList = userService.listAllUsers();

      //then - Assertion/Verification
//        assertEquals(expectedList, actualList);

        //AssertJ
        assertThat(actualList).usingRecursiveAssertion().isEqualTo(expectedList);
        verify(userRepository, atLeastOnce()).findAllByIsDeletedOrderByFirstNameDesc(false);



    }
}
