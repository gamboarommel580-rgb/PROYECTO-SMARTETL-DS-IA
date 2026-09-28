# Datos de prueba

Los cuatro archivos CSV de esta carpeta contienen datos ficticios. Los nombres `Alumno01` y los identificadores `0000000001` en adelante son marcadores de prueba; no representan estudiantes reales.

`estudiantes.csv` incluye cinco registros válidos y cinco casos rechazados: ID duplicado, edad fuera de rango, promedio fuera de rango, ID mal formado y nombre vacío. `matriculas.csv` incluye tres casos rechazados: estudiante inexistente, asignatura inexistente y estado inválido. Con el código base del ETL, el resultado esperado es **5 estudiantes válidos y 8 errores**.

Los archivos conservan encabezados y columnas compatibles con `Extract`, `Transform` y `Load`. Los resultados generados se escriben en `output/`.
