package chocolate.chocho.service.employer.store.command;

import chocolate.chocho.dto.StoreCmdDto;

import java.util.UUID;

public interface StoreCommandService {

   UUID create(StoreCmdDto dto);
}