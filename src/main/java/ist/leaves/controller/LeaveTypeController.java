package ist.leaves.controller;

import ist.leaves.entity.LeaveType;
import ist.leaves.repository.LeaveTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin/leave-types")
@PreAuthorize("hasRole('ADMIN')")
public class LeaveTypeController {

    private final LeaveTypeRepository repository;

    public LeaveTypeController(LeaveTypeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<LeaveType> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public LeaveType create(@RequestBody LeaveType leaveType) {
        return repository.save(leaveType);
    }

    @PutMapping("/{id}")
    public LeaveType update(@PathVariable Long id, @RequestBody LeaveType updated) {
        return repository.findById(id)
                .map(leaveType -> {
                    leaveType.setName(updated.getName());
                    leaveType.setDescription(updated.getDescription());
                    return repository.save(leaveType);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}