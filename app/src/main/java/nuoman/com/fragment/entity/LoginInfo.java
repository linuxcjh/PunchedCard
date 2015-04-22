package nuoman.com.fragment.entity;

/**
 * Created by Chen on 2015/4/22.
 */
public class LoginInfo {

//    {"role":"1","schoolId":"2","schoolname":"NuoMan"}

    private String role;
    private String schoolId;
    private String schoolname;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }
}
