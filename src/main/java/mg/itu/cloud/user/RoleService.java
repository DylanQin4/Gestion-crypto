package mg.itu.cloud.user;

import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public Role getRoleById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }
}
