# 🧠 Simple CPU Addition Explained

This demonstrates how a basic arithmetic operation (5 + 3) is executed inside a CPU at the microarchitecture level. It's ideal for computer science students, educators, or anyone curious about what's happening under the hood of a processor.

---

## 🔁 Step-by-Step Breakdown

| Step | Component                   | Action                                                                 |
|------|-----------------------------|------------------------------------------------------------------------|
| 1️⃣   | **Registers (A & B)**         | Load operands `5` and `3` into fast-access CPU registers.              |
| 2️⃣   | **Program Counter (PC)**      | Points to the `ADD` instruction in memory.                             |
| 3️⃣   | **Instruction Decoder (CU)** | Decodes the instruction and signals ALU to execute addition.           |
| 4️⃣   | **ALU**                      | Performs `5 + 3 = 8`; updates flags (Carry, Zero, etc.).               |
| 5️⃣   | **Result Storage**           | Stores result (`8`) in a destination register (e.g., Register C).      |
| 6️⃣   | **Next Instruction**         | PC increments to fetch and execute the next instruction.               |

---

## 💡 Related Topics

- Binary arithmetic
- CPU architecture & micro-operations
- Instruction cycle
- Control Unit & ALU design
