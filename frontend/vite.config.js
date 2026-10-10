import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// In development, /api requests are forwarded to the Spring Boot backend,
// so the backend needs no CORS configuration.
export default defineConfig({
  plugins: [react()],
  server: { proxy: { "/api": process.env.BACKEND_URL || "http://localhost:8080" } },
});
