# 📅 EventHub – Event Management & Booking Platform
### 📝 Project Description
EventHub is an event management and booking platform that allows users to discover upcoming events, reserve seats, and keep track of their bookings. It also provides tools for creating and managing events, making it easier to organize events and connect people through shared experiences.

## ✨ Features
### 🙋 Attendee Features

- 🔍 **Browse & Search Events** – Explore upcoming events and search for events of interest.
- 📄 **View Event Details** – View detailed information about events, including descriptions, dates, venues, location, capacity, and available seats.
- 🎟️ **Book Events** – Reserve seats for upcoming events.
- ❌ **Cancel Bookings** – Cancel previously made event bookings.
- 📅 **My Bookings** – Keep track of booked events and manage reservations in one place.

### 👨‍💼 Organizer Features

- 🔍 **Browse & Search Events** – Explore and search through available events.
- 📄 **View Event Details** – View detailed information about events, including descriptions, dates, venues, location, capacity, and available seats.
- ➕ **Create Events** – Create new events by providing relevant event information.
- ✏️ **Edit Events** – Update existing event information.
- 🗑️ **Delete Events** – Remove events that are no longer needed.
- 👁️ **Publish / Hide Events** – Control event visibility and availability on the platform.
- 📊 **Organizer Dashboard** – Monitor event activity and performance through an overview of:
  - Total events created
  - Total published events
  - Total seats reserved
  - Total revenue
  - Event dates, venues, and other relevant information
- 🏢 **Venue Management** – Create, edit, and delete venues for hosting events.

## 📋 Business Rules
### 👥 Users & Roles
- Users register as either an **Organizer** or an **Attendee**.
- Username and email must be unique.
- Passwords are stored hashed and must be between 8 and 64 characters.
- Each role can only access its own features (e.g. only organizers manage venues and events,
  only attendees can book).

### 🏢 Venues
- A venue's name must be unique within the same city.
- Venue capacity must be between 1 and 100,000.
- A venue's capacity cannot be reduced below the capacity of any upcoming event held there.
- A venue with upcoming or ongoing events cannot be deleted.
- A venue that only has past events is archived instead of deleted, so past event history is preserved.

### 🎟️ Events
- Start and end time must be in the future, and the end time must be after the start time.
- Event capacity cannot exceed the capacity of its venue.
- Two events cannot take place at the same venue at overlapping times.
- Only the organizer who created an event can edit, delete, publish or hide it.
- An event cannot be edited once it has started.
- Event capacity cannot be reduced below the number of seats already booked.
- The visibility (publish/hide) of an event cannot be changed after it has ended.
- An event with active bookings cannot be deleted.
- Hidden events are visible only to their organizer and to attendees who already booked them.

### 🎫 Bookings
- Only attendees can book events.
- Only published events that have not started yet can be booked.
- An attendee can have only one active booking per event.
- At least 1 seat must be booked, and bookings cannot exceed the available seats.
- Attendees can cancel only their own confirmed bookings, and only before the event starts.
- Cancelled bookings free up their seats for other attendees.

## 🛠️ Tech Stack
### ⚙️ Backend
- Java 17
- Spring Boot 3.4.0
- Spring Web (MVC)
- Spring Data JPA (Hibernate)
- Spring Validation (Jakarta Bean Validation)
- Spring Security Crypto (Password Hashing)
- Lombok

### 🗄️ Database
- MySQL

### 🎨 Frontend
- Thymeleaf
- HTML & CSS
- Bootstrap Icons

### 🔨 Build Tool
- Maven

## 🚀 Getting Started
### 📋 Prerequisites
- Java 17 or higher
- MySQL running on `localhost:3306`
- IntelliJ IDEA

### ⚙️ Installation

1. Clone the repository and open the project in **IntelliJ IDEA**.
   
2. Create a run configuration with the database credentials:
   * Go to **Run → Edit Configurations...**
   * Click **+** (Add New Configuration) and select **Spring Boot**.
   * Fill in the configuration:
     * **Name:** `EventhubApplication`
     * **JDK:** Java 17 or higher
     * **Main class:** `com.eventhub.EventhubApplication`
     * **Environment variables:**
       ```
       DB_USERNAME=your_mysql_username;DB_PASSWORD=your_mysql_password
       ```
       *(If the field is not visible, click **Modify options → Environment variables**.)*
   * Click **Apply** and **OK**.
     
3. Run the `EventhubApplication` configuration using the green ▶ button in the top toolbar.
   
4. Open http://localhost:8080 in your browser.

### 🎮 Usage
- Register an **Organizer** account to create venues and events.
- Register an **Attendee** account to browse and book events.

## 📸 Screenshots
### 🏠 Index Page
<img width="400" alt="Index Page" src="https://github.com/user-attachments/assets/a073d921-d379-4ca3-80ac-84011c78ec86" />

### 🎟️ Events Page
<img width="400" alt="Events Page" src="https://github.com/user-attachments/assets/c845270a-df61-42e2-8944-2e0334bd0a8c" />

### 📄 Event Details Page (Attendee View)
<img width="400" alt="Event Details Page - Attendee View" src="https://github.com/user-attachments/assets/e809155d-5b2d-47a3-bedb-4b017281d335" />

### 📄 Event Details Page (Organizer View)
<img width="400" alt="Event Details Page - Organizer View" src="https://github.com/user-attachments/assets/1b10e3f6-7f7e-43b4-9527-d5342a3533ae" />

### 🏢 Venues Page
<img width="400" alt="Venues Page" src="https://github.com/user-attachments/assets/13d92c0d-4555-4f06-82ed-5ca58965e9bc" />

### 📊 Dashboard Page
<img width="400" alt="Dashboard Page" src="https://github.com/user-attachments/assets/c7aa5b82-143f-4055-8097-e72c01de9b8f" />

### 📅 My Bookings Page
<img width="400" alt="My Bookings Page" src="https://github.com/user-attachments/assets/e271cc49-1271-4a8c-a995-725dd116cb5c" />
