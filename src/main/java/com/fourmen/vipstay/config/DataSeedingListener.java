package com.fourmen.vipstay.config;

import com.fourmen.vipstay.model.*;
import com.fourmen.vipstay.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private ImageHouseRepository imageHouseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        // record into database all role name of RoleName enum
        for (RoleName roleName : RoleName.values()) {
            if (roleRepository.findByName(roleName) == null) {
                roleRepository.save(new Role(roleName));
            }
        }

        // set some default user
        String[] avatar1 = new String[]{"https://i0.wp.com/www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png?fit=256%2C256&quality=100&ssl=1",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQdZJR0V-0Gj4v1fYHNoYvF0zHUqKK9q0CnMN8-_ve9AcX1nJUx",
                "https://banner2.kisspng.com/20180325/kxe/kisspng-teacher-education-student-course-school-avatar-5ab752767ae3f6.7668647915219636385034.jpg"};
        String[] userDefaultStr = new String[]{"admin", "host", "guest"};
        int j=0;
        for (String userStr : userDefaultStr) {
            if (userRepository.findByUsername(userStr) == null) {
                User user = new User();
                String email = userStr + "1@gmail.com";
                user.setEmail(email);
                user.setName(userStr);
                user.setUsername(userStr);
                user.setPassword(passwordEncoder.encode("123456"));
                user.setAvatar(avatar1[j]);
                Set<Role> roles = new HashSet<>();
                RoleName roleName = userStr.equals("admin") ? RoleName.ROLE_ADMIN :
                        userStr.equals("host") ? RoleName.ROLE_HOST : RoleName.ROLE_GUEST;
                roles.add(roleRepository.findByName(roleName));
                user.setRoles(roles);
                userRepository.save(user);
                j++;
            }
        }

        String[] avatar2=new String[]{"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTkhdUUbnEjgVV3Py5PT2dP-hhdEK_ID7s4PuyJ3j02_Wbf8xK1",
        "https://banner2.kisspng.com/20180325/kxe/kisspng-teacher-education-student-course-school-avatar-5ab752767ae3f6.7668647915219636385034.jpg",
        "http://blog.gosocket.net/wp-content/uploads/2016/03/test1.png",
        "https://cdn.iconscout.com/icon/free/png-256/avatar-380-456332.png"};
        String[] users = new String[]{"Dat", "Khanh", "Thao", "Lam"};
        User[] userList = new User[4];
        int stt = 0;
        for (String userStr : users) {
            if (userRepository.findByUsername(userStr) == null) {
                User user = new User();
                String email = userStr + "@gmail.com";
                user.setEmail(email);
                user.setName(userStr);
                user.setUsername(userStr);
                user.setPassword(passwordEncoder.encode("123456"));
                user.setAvatar(avatar2[stt]);
                Set<Role> roles = new HashSet<>();
                RoleName roleName = RoleName.ROLE_HOST;
                roles.add(roleRepository.findByName(roleName));
                user.setRoles(roles);
                userList[stt] = user;
                stt++;
                userRepository.save(user);
            }
        }

        //set default category data
        String[] categories = new String[]{"House", "Villa", "Resort", "Hotel"};
        for (String category : categories) {
            if (categoryRepository.findByName(category) == null) {
                categoryRepository.save(new Category(category));
            }
        }

        //set list house
        String[] addresses = new String[]{"Hà Nội", "Hồ Chí Minh", "Đà Lạt", "Đà Nẵng", "SaPa", "Huế", "Hạ Long", "Nha Trang", "Phan Thiết", "Đồ Sơn","Hà Giang","Vũng Tàu"};
        String[] houseNames = new String[]{"Đạt's House", "Khánh's House", "Thảo's House", "Lâm's House", "Nam's House", "Tuấn Anh's House", "Văn's House", "Tiên's House", "Tình's House", "Giang's House","Hòa's House","Hoàng's House"};
        String[] descriptions = new String[]{"Dien tich 1000m2, day du trang thiet bi", "Dien tich 600m2, nha huong Dong Nam", "Dien tich 750 m2, sale off 20%", "Dien tich 800 m2, nha gan trung tam thanh pho", "Dien tich 900 m2, nha 4 mat tien, thoang mat", "Dien tich 800 m2, nha thoang mat, ve sinh sach se", "Dien tich 1200 m2, can nha smartHouse", "Dien tich 650 m2, nha phong cach tan co dien", "Dien tich 900 m2, gan bien va cac khu giai tri", "Dien tich 989 m2, gan trung tam thanh pho", "Dien tich 900 m2, gan bien va cac khu giai tri", "Dien tich 989 m2, gan trung tam thanh pho"};
        int[] bathrooms = new int[]{2, 3, 4, 3, 5, 3, 4, 4, 2, 5,4,5};
        int[] bedrooms = new int[]{2, 3, 4, 3, 5, 3, 4, 4, 2, 5,5,4};
        int[] prices = new int[]{1000, 400, 400, 300, 500, 300, 400, 400, 200, 500,999,666};
        int[] rates = new int[]{5, 3, 4, 2, 3, 4, 5, 3, 4, 4,5,5};
        int[] areas = new int[]{100, 70, 50, 80, 120, 80, 90, 60, 100, 90,100,100};
        String[] cagories = new String[]{"Hotel", "Villa", "Resort", "Home"};
        String[][] imageHouses = new String[][]{
                {"https://cdn.luxstay.com/rooms/24507/large/room_24507_292_1557131217.jpg",
                        "https://cdn.luxstay.com/rooms/24507/large/room_24507_291_1557131215.jpg",
                        "https://cdn.luxstay.com/rooms/24507/large/room_24507_275_1557131197.jpg"},
                {"https://cdn.luxstay.com/rooms/26860/large/room_26860_193_1567071703.jpg",
                        "https://cdn.luxstay.com/rooms/26860/large/room_26860_191_1567071699.jpg",
                        "https://cdn.luxstay.com/rooms/26860/large/room_26860_185_1567071691.jpg"},
                {"https://cdn.luxstay.com/rooms/18878/large/room_18878_6_1546406339.jpg",
                        "https://cdn.luxstay.com/rooms/18878/large/room_18878_186_1567153085.jpg",
                        "https://cdn.luxstay.com/rooms/18878/large/room_18878_185_1567153084.jpg"},
                {"https://cdn.luxstay.com/rooms/26385/large/room_26385_35_1560698760.jpg",
                        "https://cdn.luxstay.com/rooms/26385/large/room_26385_32_1560698757.jpg",
                        "https://cdn.luxstay.com/rooms/26385/large/room_26385_29_1560698753.jpg"},
                {"https://cdn.luxstay.com/rooms/17189/large/room_17189_2_1542693223.jpg",
                        "https://cdn.luxstay.com/rooms/17189/large/room_17189_1542688990.jpg",
                        "https://cdn.luxstay.com/rooms/17189/large/room_17189_1542688851.jpg"},
                {"https://cdn.luxstay.com/rooms/16902/large/1541932163_2018_11_10_025515_6jpg",
                        "https://cdn.luxstay.com/rooms/16902/large/1541932159_2018_11_10_025514_3jpg",
                        "https://cdn.luxstay.com/rooms/16902/large/1541932150_45822218_519650318502726_3570340621640007680_njpg"},
                {"https://cdn.luxstay.com/rooms/12307/large/room_12307_37_1550635922.jpg",
                        "https://cdn.luxstay.com/rooms/12307/large/room_12307_40_1550635970.jpg",
                        "https://cdn.luxstay.com/rooms/12307/large/room_12307_50_1551089670.jpg"},
                {"https://cdn.luxstay.com/rooms/15702/large/1538726023_Nha-hang%20(8).jpg",
                        "https://cdn.luxstay.com/rooms/15702/large/1538726022_bungalow-view-3.jpg",
                        "https://cdn.luxstay.com/rooms/15702/large/1538726020_canh-view-4.jpg"},
                {"https://cdn.luxstay.com/rooms/12568/large/room_12568_3_1563176361.jpg",
                        "https://cdn.luxstay.com/rooms/12568/large/1529656178__MG_8909.jpg",
                        "https://cdn.luxstay.com/rooms/12568/large/1527155542__MG_8833.jpg"},
                {"https://cdn.luxstay.com/rooms/13338/large/1531106498_LEP_1354.jpg",
                        "https://cdn.luxstay.com/rooms/13338/large/1531106496_LEP_1360.jpg",
                        "https://cdn.luxstay.com/rooms/13338/large/1531106461_LEP_1291.jpg"},
                {"https://cdn.luxstay.com/rooms/26385/large/room_26385_35_1560698760.jpg",
                        "https://cdn.luxstay.com/rooms/26385/large/room_26385_32_1560698757.jpg",
                        "https://cdn.luxstay.com/rooms/26385/large/room_26385_29_1560698753.jpg"},
                {"https://cdn.luxstay.com/rooms/17189/large/room_17189_2_1542693223.jpg",
                        "https://cdn.luxstay.com/rooms/17189/large/room_17189_1542688990.jpg",
                        "https://cdn.luxstay.com/rooms/17189/large/room_17189_1542688851.jpg"}
        };
        for (int i = 0; i < 12; i++) {
            House house = new House();
            house.setHouseName(houseNames[i]);
            boolean isExistHouse = houseRepository.findByHouseName(house.getHouseName()) == null;
            if (isExistHouse) {
                house.setStatus(Status.AVAILABLE);
                house.setAddress(addresses[i]);
                house.setDescription(descriptions[i]);
                house.setBathroomNumber((long) bathrooms[i]);
                house.setBedroomNumber((long) bedrooms[i]);
                house.setArea((long) areas[i]);
                house.setPrice((long) prices[i]);
                house.setRate((long) rates[i]);
                if (i < 4) {
                    house.setUser(userList[i]);
                    Category category = new Category(cagories[i]);
                    category.setId((long) i + 1);
                    house.setCategory(category);
                } else if (i < 8) {
                    house.setUser(userList[i - 4]);
                    Category category = new Category(cagories[i - 4]);
                    category.setId((long) i - 3);
                    house.setCategory(category);

                } else {
                    house.setUser(userList[i - 8]);
                    Category category = new Category(cagories[i - 8]);
                    category.setId((long) i - 7);
                    house.setCategory(category);
                }
                this.houseRepository.save(house);
                //save images for house
                ImageOfHouse imageOfHouse1 = new ImageOfHouse(imageHouses[i][0], house);
                this.imageHouseRepository.save(imageOfHouse1);
                ImageOfHouse imageOfHouse2 = new ImageOfHouse(imageHouses[i][1], house);
                this.imageHouseRepository.save(imageOfHouse2);
                ImageOfHouse imageOfHouse3 = new ImageOfHouse(imageHouses[i][2], house);
                this.imageHouseRepository.save(imageOfHouse3);
            }
        }

    }
}
