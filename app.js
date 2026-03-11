const state = {
  raw: null,
  workerSkill: 'all',
  workerQuery: '',
  equipmentStatus: 'all'
};

const el = {
  statsGrid: document.getElementById('statsGrid'),
  workerList: document.getElementById('workerList'),
  workerTags: document.getElementById('workerTags'),
  workerSearch: document.getElementById('workerSearch'),
  equipmentList: document.getElementById('equipmentList'),
  equipmentFilter: document.getElementById('equipmentFilter'),
  allocationList: document.getElementById('allocationList'),
  addDemoRequest: document.getElementById('addDemoRequest'),
  menuToggle: document.getElementById('menuToggle'),
  mainNav: document.getElementById('mainNav')
};

function renderStats() {
  const workers = state.raw.workers;
  const equipment = state.raw.equipment;
  const stats = [
    { label: 'Total Workers', value: workers.length },
    { label: 'Available Workers', value: workers.filter(w => w.availability === 'available').length },
    { label: 'Equipment In Use', value: equipment.filter(e => e.status === 'in-use').length },
    { label: 'Active Projects', value: state.raw.allocations.length }
  ];

  el.statsGrid.innerHTML = stats.map(s => `
    <article class="stat">
      <p>${s.label}</p>
      <strong>${s.value}</strong>
    </article>
  `).join('');
}

function renderWorkerTags() {
  const skills = ['all', ...new Set(state.raw.workers.map(worker => worker.skill))];
  el.workerTags.innerHTML = skills.map(skill => `
    <button class="tag ${state.workerSkill === skill ? 'active' : ''}" data-skill="${skill}">
      ${skill}
    </button>
  `).join('');
}

function renderWorkers() {
  const filtered = state.raw.workers.filter(worker => {
    const skillOk = state.workerSkill === 'all' || worker.skill === state.workerSkill;
    const query = state.workerQuery.trim().toLowerCase();
    const queryOk = !query || worker.name.toLowerCase().includes(query) || worker.skill.toLowerCase().includes(query);
    return skillOk && queryOk;
  });

  el.workerList.innerHTML = filtered.map(worker => `
    <article class="item">
      <div>
        <h4>${worker.name} <span class="badge ${worker.availability}">${worker.availability}</span></h4>
        <p>${worker.skill} • ${worker.experience} years exp • ${worker.id}</p>
      </div>
    </article>
  `).join('') || '<p>No manpower found for selected filters.</p>';
}

function renderEquipment() {
  const filtered = state.raw.equipment.filter(eq => state.equipmentStatus === 'all' || eq.status === state.equipmentStatus);
  el.equipmentList.innerHTML = filtered.map(eq => `
    <article class="item">
      <div>
        <h4>${eq.name} <span class="badge ${eq.status}">${eq.status}</span></h4>
        <p>${eq.id} • Next service: ${eq.nextService}</p>
      </div>
    </article>
  `).join('') || '<p>No equipment in this status.</p>';
}

function renderAllocations() {
  el.allocationList.innerHTML = state.raw.allocations.map((item, index) => `
    <article class="item">
      <div>
        <h4>${item.project}</h4>
        <p>${item.workers} workers • ${item.equipment} equipment assigned</p>
      </div>
      <span class="badge ${item.priority === 'High' ? 'critical' : 'available'}">${item.priority}</span>
    </article>
  `).join('');
}

function bindEvents() {
  el.workerTags.addEventListener('click', (event) => {
    const btn = event.target.closest('[data-skill]');
    if (!btn) return;
    state.workerSkill = btn.dataset.skill;
    renderWorkerTags();
    renderWorkers();
  });

  el.workerSearch.addEventListener('input', (event) => {
    state.workerQuery = event.target.value;
    renderWorkers();
  });

  el.equipmentFilter.addEventListener('change', (event) => {
    state.equipmentStatus = event.target.value;
    renderEquipment();
  });

  el.addDemoRequest.addEventListener('click', () => {
    state.raw.allocations.unshift({
      project: `New Client Request ${state.raw.allocations.length + 1}`,
      workers: 4,
      equipment: 1,
      priority: 'High'
    });
    renderAllocations();
    renderStats();
  });

  el.menuToggle.addEventListener('click', () => {
    el.mainNav.classList.toggle('open');
  });
}

async function initApp() {
  const response = await fetch('./data/mock-data.json');
  state.raw = await response.json();
  renderStats();
  renderWorkerTags();
  renderWorkers();
  renderEquipment();
  renderAllocations();
  bindEvents();
}

initApp();
