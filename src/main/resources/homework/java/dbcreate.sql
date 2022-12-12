create table Aircrafts
(
    aircraft_code char(3) not null primary key,
    model         JSON    not null,
    range         int     not null
);

create table Airports
(
    airport_code char(3) not null primary key,
    airport_name JSON    not null,
    city         JSON    not null,
    coordinates  text    not null,
    timezone     text    not null
);

create table Boarding_passes
(
    ticket_no   char(13)   not null,
    flight_id   int        not null,
    boarding_no int        not null,
    seat_no     varchar(4) not null,
    primary key (ticket_no, flight_id)
);


create table Bookings
(
    book_ref     char(6)        not null primary key,
    book_date    timestamp      not null,
    total_amount numeric(10, 2) not null
);

create table Flights
(
    flight_id           int         not null primary key,
    flight_no           char(6)     not null,
    scheduled_departure timestamp   not null,
    scheduled_arrival   timestamp   not null,
    departure_airport   char(3)     not null,
    arrival_airport     char(3)     not null,
    status              varchar(20) not null,
    aircraft_code       char(3)     not null,
    actual_departure    timestamp,
    actual_arrival      timestamp
);

create table Seats
(
    aircraft_code   varchar(3)  not null,
    seat_no         varchar(4)  not null,
    fare_conditions varchar(10) not null
    -- foreign key (aircraft_code) references aircrafts (aircraft_code)
);

create table Ticket_flights
(
    ticket_no       varchar(13)    not null,
    flight_id       int            not null,
    fare_conditions varchar(10),
    amount          numeric(10, 2) not null,
    primary key (ticket_no, flight_id)
);

create table Tickets
(
    ticket_no      char(13)    not null primary key,
    book_ref       char(6)     not null,
    passenger_id   varchar(20) not null,
    passenger_name text        not null,
    contact_data   text
)