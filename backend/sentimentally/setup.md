
# Setting Up PostgreSQL Database and User via pgAdmin

This guide walks you through creating a PostgreSQL database and user account using pgAdmin.

## 🔧 Connection Details

- **Database name:** `sentimentally`
- **Username:** `sentimentally`
- **Password:** `sentimentally@some4digits`
- **Host:** `localhost`
- **Port:** `5432` (default)

---

## 📘 Option 1: Using pgAdmin GUI

### Step 1: Create a Database
1. Open **pgAdmin**.
2. Connect to your PostgreSQL server (e.g., `PostgreSQL 14`, `PostgreSQL 15`, etc.).
3. In the Object Browser (left panel), right-click on **Databases** → Click **Create** → **Database…**.
4. In the dialog:
   - **Database:** `sentimentally`
   - **Owner:** leave as `postgres` (or your superuser)
5. Click **Save**.

### Step 2: Create a New Role (User)
1. In the Object Browser, find **Login/Group Roles**:
   ```
   Servers
   └── PostgreSQL 14 (or your version)
       ├── Databases
       ├── Login/Group Roles  ⬅️ Click here
       └── Tablespaces
   ```
2. Right-click **Login/Group Roles** → Click **Create** → **Login/Group Role…**.
3. In the dialog:
   - **Name:** `sentimentally`
4. Go to the **Definition** tab:
   - **Password:** `sentimentally@some4digits`
5. Go to the **Privileges** tab:
   - Enable **Can login and can create databases**
6. Click **Save**.

### Step 3: Grant Access to Database
1. In the Object Browser, right-click the `sentimentally` database → Click **Properties**.
2. Go to the **Privileges** tab.
3. Click the ➕ button to add a new role.
   - **Role:** `sentimentally`
   - Check **Connect**, **Temporary**, **Create**, all of the available checkboxes.
4. Click **Save**.

## ✅ After Setup

Your JDBC connection should work with the following:
```
jdbc:postgresql://localhost:5432/sentimentally
```
- **Username:** `sentimentally`
- **Password:** `sentimentally@some4digits`

Make sure PostgreSQL is running and your app has access to `localhost:5432`.

---

# Setting Up database connection and running backend application

This guide walks you through setting Up database connection and running backend spring application using intellij.

## Defining source folder

- Right click on the src/main/java folder and mark
- Click on Mark Directory as → Sources Root.


## 🔧 application.yaml changes
   ```
   datasource:
      url: jdbc:postgresql://localhost:5432/sentimentally
      username: sentimentally
      password: sentimentally@some4digits
      driver-class-name: org.postgresql.Driver
   ```

## Example structure of spring project

```
sentimentally/
├── src/
│   └── main/
│       ├── java/ -> source root folder
│       │   └── com/main/sentimentally/
│       │       └── SentimentallyApplication.java
│       └── resources/
│           └── application.yaml
├── pom.xml

```