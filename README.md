# Cloud Instance Manager

Cloud Instance Manager is an application designed to handle instances from AWS and Azure. The application comprises three main services built on MongoDB:

1. **Instance Service:**
   - Manages tasks related to instances, such as listing instances, changing the state (start/stop), retrieving the instance status, and obtaining instance details.
   - Supports instance creation (currently AWS is supported).

2. **Lease Service:**
   - Allows the creation and update of leases for instances.
   - A lease determines when an instance should start or stop, taking parameters like instance name, start date, start time, end date, end time, alwaysOn, and weekendOn.

3. **Trigger Service:**
   - Generates and takes actions on triggers associated with instances (e.g., when the instance should start or stop).
   - Contains details like fireTime, action, instance name, and lease reference ID.

## Project Structure

The project is organized into two main branches:

1. **non-reactive:**
   - Monolithic architecture implementation of the application.
   - [View this branch](https://github.com/paliwal999harsh/cloud-instance-manager/tree/non-reactive)

2. **non-reactive-microservices-arch:**
   - Microservices architecture implementation.
   - [View this branch](https://github.com/paliwal999harsh/cloud-instance-manager/tree/non-reactive-microservices-arch)

## Technology Stack

- MongoDB for data storage.
- SpringBoot
- React
- Docker