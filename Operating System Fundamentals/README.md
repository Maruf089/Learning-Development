Operating System Fundamentals

Parallelism vs Concurrency
Concurrency is dealing with multiple tasks at once. It makes the system switch between tasks and make progress in them turn by turn. Parallelism is kind of multiple CPU cores running tasks individually but simultaneously.

🧵 Threads vs. ⚙️ Processes: What Makes Threads "Cheaper"?
1. Memory Usage

    Processes each have their own memory space — they don’t share memory with other processes.

    Threads share the same memory space with other threads in the same process.

    👉 Less overhead because threads don’t need separate allocations.

2. Creation Time

    Creating a process involves duplicating resources and setting up a whole new environment.

    Creating a thread is faster since it just needs a stack and thread control block — no need to duplicate everything.

3. Context Switching

    Switching between processes involves saving and loading all memory mappings, file descriptors, and more.

    Switching between threads is quicker because they share much of their environment.

4. Communication

    Inter-process communication (IPC) like pipes or sockets can be complex and slow.

    Threads communicate more easily because they share memory.

5. CPU Scheduling

    Threads are often managed within the same process, allowing for smoother task scheduling.

    Processes are isolated and need more coordination from the OS.

📌 Example: Think of processes as people living in different houses — communicating takes phone calls or emails. Threads are roommates — they just shout across the room!


| Aspect            | Process               | Thread               | Winner   |
| ----------------- | --------------------- | -------------------- | -------- |
| Memory usage      | High (separate space) | Low (shared memory)  | ✅ Thread |
| Creation time     | Slow                  | Fast                 | ✅ Thread |
| Context switching | Expensive             | Lightweight          | ✅ Thread |
| Communication     | Complex (IPC)         | Simple (shared vars) | ✅ Thread |


Context switching
Context switching is about saving the state of current process and restoring the state of another process so that it can resume execution. By using this process multiple process share the same CPU.


🛡️ What Are Kernel Mode and User Mode?

    Kernel Mode: Full access to all system resources — the OS core operates here.

    User Mode: Restricted access — applications and user-level processes run here to prevent system-wide damage.

🔐 How This Separation Is Maintained and Secured
1. CPU Privilege Levels

    Modern CPUs have hardware-enforced privilege levels.

    Most use two levels:

        Ring 0 (Kernel Mode): Highest privilege

        Ring 3 (User Mode): Lowest privilege

    Only the OS kernel runs in Ring 0, ensuring sensitive instructions can’t be executed in user mode.

2. System Calls

    User-mode applications cannot directly access kernel resources.

    Instead, they use system calls — controlled gateways the OS exposes.

    The transition involves switching the CPU to kernel mode temporarily, executing the request, then returning to user mode.

3. Memory Protection

    The OS uses hardware features (like MMU — Memory Management Unit) to mark kernel memory as inaccessible to user-mode processes.

    If a user-mode process tries to access kernel memory: 🚫 Access violation! The OS will terminate or restrict the process.

4. Process Isolation

    Each process runs in its own virtual memory space.

    This prevents rogue or buggy apps from tampering with others — or the kernel itself.

5. Interrupt Handling & I/O

    Interrupts and device I/O operations are handled exclusively in kernel mode.

    User-mode apps request these via system calls, preserving controlled access.

6. Security Checks & Validation

    The OS validates all inputs from user-mode before acting.

    For example, copying data from user space to kernel space is done carefully to prevent buffer overflows or injection attacks.

🧠 Why Does This Matter?

This separation prevents malicious apps from accessing your device's core functions. It’s like a castle with a secure throne room — only trusted guards (kernel processes) can enter. Everyone else (user apps) must stay outside the gate and knock politely.


How to allocate swap space in linux?

To allocate swap space in Linux, you can either:

    ✅ Create a swap file (most common for modern systems), or

    ✅ Create a swap partition (common in older setups or servers with pre-partitioned disks)

Here's how to do it using a swap file (safe and flexible):

🧾 Step-by-Step: Create a Swap File
1. Create the swap file

sudo fallocate -l 2G /swapfile

    This creates a 2GB swap file. You can change 2G to any size you need.

    If fallocate fails, use:

    sudo dd if=/dev/zero of=/swapfile bs=1M count=2048

2. Secure the file

sudo chmod 600 /swapfile

3. Mark it as swap

sudo mkswap /swapfile

4. Enable the swap file

sudo swapon /swapfile

5. Make it permanent (across reboots)

Add this line to /etc/fstab:

/swapfile none swap sw 0 0

🔍 Check if Swap is Active

swapon --show
free -h


What data structures you will use for LRU cache implementation?

I will use HashMap + Doubly Linked List for a classic implementation.
I will use LinkedHashMap for a quick, shorter Java solution

If allowed, Java provides LinkedHashMap which maintains insertion/access order:

class LRUCache extends LinkedHashMap<Integer, Integer> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true); // 'true' for access-order
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}

⏱ Time & Space Complexity:

    Get / Put: O(1)

    Space: O(capacity)



Explain role of stack and heap for process management

Manages function calls, parameters, local variables, and return addresses.

Grows and shrinks automatically as functions are called and return.

| Feature           | Stack                               |
| ----------------- | ----------------------------------- |
| Memory allocation | **Automatic**                       |
| Size              | Fixed (limited, per process/thread) |
| Lifespan          | Tied to function scope              |
| Access speed      | Very fast (LIFO structure)          |
| Management        | Handled by compiler/OS              |


Process used for dynamic memory allocation.
Managed manually or via garbage collection.

| Feature           | Heap                            |
| ----------------- | ------------------------------- |
| Memory allocation | **Manual or garbage-collected** |
| Size              | Large, can grow dynamically     |
| Lifespan          | Until explicitly freed or GC’d  |
| Access speed      | Slower than stack               |
| Management        | By programmer or runtime        |



⚠️ What is a Race Condition?

A race condition is a type of concurrency bug that occurs when two or more threads/processes access shared data at the same time, and the final outcome depends on the timing or order of execution.
🧠 Key Concept:

    Race condition = Uncontrolled access to shared resources.

It leads to unexpected behavior, inconsistent results, or crashes.


🔒 What is a Deadlock?

A deadlock is a situation in concurrent systems (like multithreaded programs or operating systems) where two or more processes are blocked forever, each waiting for the other to release a resource.
🧠 Key Idea:

    A deadlock happens when processes hold resources and wait for others, forming a circular chain of dependency — and none can proceed.

🧩 Example Scenario:
Imagine two threads:

    Thread A holds Lock 1 and waits for Lock 2

    Thread B holds Lock 2 and waits for Lock 1

➡️ Both are stuck, waiting for the other — this is a deadlock.
💡 Real-World Analogy:

Two people trying to pass through a narrow hallway from opposite directions:

    Each blocks the other.

    Neither can proceed until the other backs off.

    But neither is willing to move first.


🔐 What is Optimistic Locking?

Optimistic locking is a concurrency control strategy where conflicts are assumed to be rare, so multiple threads/processes can read and modify shared data without locking it immediately.
Instead, the system checks for conflicts before committing changes.

🔄 How Optimistic Locking Works

    Read the data (along with a version number or timestamp)

    Perform operations in memory — without locking the resource

    Before committing, check whether the data has changed:

        Compare the version/timestamp with the current one in the database

    If unchanged → Proceed with update ✅ If changed → Conflict detected ❌ — transaction fails or retries


🏷️ Common Types of Pessimistic Locking
1. Exclusive Lock (Write Lock)

    Prevents read and write access from other transactions.

    Only one transaction can access the resource.

    Used when modification is anticipated.

    Example: SEMAPHORE

2. Shared Lock (Read Lock)

    Allows multiple transactions to read, but none can modify the data.

    Prevents dirty reads and ensures read consistency.

    example : MUTEX

3. Update Lock

    Used in systems like SQL Server.

    Prevents deadlock during lock escalation by holding a temporary lock that converts to exclusive when the transaction decides to update.

    Only one transaction can hold an update lock on a resource.



⚖️ Why MySQL (InnoDB) is Less Prone to Deadlocks:
✅ 1. Smaller Lock Granularity

    MySQL (InnoDB) uses row-level locks by default.

    It avoids locking more than necessary, reducing contention.

✅ 2. Consistent Lock Ordering

    InnoDB tries to acquire locks in a predictable order (by primary key).

    This prevents circular wait conditions (a key cause of deadlocks).

✅ 4. Auto Deadlock Detection and Recovery

    MySQL quickly detects deadlocks and aborts one transaction automatically.

    Keeps the system moving.

⚠️ Why Oracle Is More Prone to Deadlocks:
❌ 1. Manual Lock Management

    Oracle uses multi-version concurrency control (MVCC), but developers often introduce explicit locks (SELECT FOR UPDATE), which increases deadlock risk.

❌ 3. More Complex Lock Scenarios

    Oracle supports fine-grained locks, enqueue locks, DDL locks, etc.

    These increase the risk of interdependencies and circular waits.

⚛️ What is an Atomic Operation?

An atomic operation is an indivisible and uninterruptible operation — meaning:

    It either completes fully, or does not happen at all — and no other thread can observe it in an intermediate state.

🧠 Why It's Important:

In multithreaded or concurrent systems, atomicity is crucial to prevent:

    Race conditions

    Inconsistent data

    Corrupted states

✅ Summary:

    An atomic operation is one that is performed as a single, uninterruptible unit.
    Maintained through CPU-level atomic instructions, memory fences, and language-level atomic libraries.
    They are essential for building thread-safe, lock-free code.


 A 32-bit CPU can address up to 2³² memory locations, which equals 4,294,967,296 bytes or 4GB of RAM.

 Why L1, L2, L3 caches are there?
 Modern CPUs have a hierarchy of caches (L1, L2, L3) to bridge the speed gap between the CPU and main memory (RAM). These levels offer a trade-off between speed, size, and cost. Having L1, L2, and L3 caches balances speed, cost, and power efficiency, ensuring the CPU runs at full speed with minimal stalls.


 🏃‍♂️ What is Variable Escaping?

Variable escaping refers to a situation where a local variable (usually from a method or function) "escapes" its original scope, typically because it's:

    Stored somewhere else (e.g., in a heap-allocated object)


What is MUTEX? Mention a scenario where you will use MUTEX

A mutex ensures mutual exclusion by allowing only one thread at a time to enter a critical section, thereby preventing race conditions in shared resource access. Bank Account Balance Update: mutex ensures only one thread modifies the balance at a time, preventing inconsistencies

What is SEMAPHORE? Mention a scenario where you will use SEMAPHORE
It allows multiple threads to access the resource up to a certain limit. Scenario: fix number of database connection pool

What is RWMUTEX? Mention a scenario where you will use RWMUTEX

Multiple threads to read a shared resource concurrently
But only one thread to write, and no readers can read while a writer is active.
Building a thread-safe in-memory cache:
Many threads can read the cache at the same time. But if one thread writes/updates, it must block all other access.


