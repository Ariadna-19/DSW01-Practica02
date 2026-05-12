export type Empleado = {
  clave: string;
  nombre: string;
  direccion: string;
  telefono: string;
  departamentoId: number | null;
  username: string | null;
};

export type EmpleadoPayload = {
  nombre: string;
  direccion: string;
  telefono: string;
  username: string;
  password: string;
  departamentoId: number;
};

export type EmpleadoPageResponse = {
  content: Empleado[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
};
