import http from 'k6/http';
import { check, sleep } from 'k6';

// Configuración general
export const options = {
    stages: [
        { duration: '30s', target: 50 },
        { duration: '1m', target: 100 },
        { duration: '30s', target: 0 },
    ],
    thresholds: {
        http_req_duration: ['p(95)<500'],
        http_req_failed: ['rate<0.01'],
    },
};

// Variables globales
const BASE_URL = 'https://petstore3.swagger.io/api/v3';
const headers = { 'Content-Type': 'application/json' };

function randomId() {
    return Math.floor(Math.random() * 100000);
}

// 🐾 1. Crear una nueva mascota
export function createPet() {
    const payload = JSON.stringify({
        id: randomId(),
        name: `doggie_${randomId()}`,
        status: 'available',
    });

    const res = http.post(`${BASE_URL}/pet`, payload, { headers });
    check(res, {
        'Create Pet - status is 200': (r) => r.status === 200,
        'Create Pet - response time < 500ms': (r) => r.timings.duration < 500,
    });
}

// 🐾 2. Obtener una mascota por ID
export function getPetById() {
    const petId = 1; // Asegúrate de que este ID exista
    const res = http.get(`${BASE_URL}/pet/${petId}`, { headers });
    check(res, {
        'Get Pet by ID - status is 200': (r) => r.status === 200,
        'Get Pet by ID - response time < 500ms': (r) => r.timings.duration < 500,
    });
}

// 🐾 3. Actualizar una mascota
export function updatePet() {
    const payload = JSON.stringify({
        id: 1,
        name: 'doggie_updated',
        status: 'sold',
    });

    const res = http.put(`${BASE_URL}/pet`, payload, { headers });
    check(res, {
        'Update Pet - status is 200': (r) => r.status === 200,
        'Update Pet - response time < 500ms': (r) => r.timings.duration < 500,
    });
}

// 🐾 4. Eliminar una mascota
export function deletePet() {
    const petId = 1;
    const res = http.del(`${BASE_URL}/pet/${petId}`, null, { headers });
    check(res, {
        'Delete Pet - status is 200': (r) => r.status === 200,
        'Delete Pet - response time < 500ms': (r) => r.timings.duration < 500,
    });
}

// 🐾 5. Buscar mascotas por estado
export function findPetsByStatus() {
    const res = http.get(`${BASE_URL}/pet/findByStatus?status=available`, { headers });
    check(res, {
        'Find Pets by Status - status is 200': (r) => r.status === 200,
        'Find Pets by Status - response time < 500ms': (r) => r.timings.duration < 500,
    });
}

// 🐾 6. Subir una imagen para una mascota
export function uploadPetImage() {
    const petId = 1;
    const formData = {
        file: http.file('test_image.jpg', 'test_image.jpg'),
    };
    const res = http.post(`${BASE_URL}/pet/${petId}/uploadImage`, formData);
    check(res, {
        'Upload Pet Image - status is 200': (r) => r.status === 200,
        'Upload Pet Image - response time < 500ms': (r) => r.timings.duration < 500,
    });
}

// 🏃‍♂️ Escenario principal
export default function () {
    createPet();
    sleep(1);
    getPetById();
    sleep(1);
    updatePet();
    sleep(1);
    findPetsByStatus();
    sleep(1);
    uploadPetImage();
    sleep(1);
    deletePet();
}
