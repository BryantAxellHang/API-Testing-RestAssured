Feature: Login API

  Background: 
  User logs in with valid credentials
    Given User has email "bryantaxell2@gmail.com" and password "@dmin123"
    When User sends POST request to "/webhook/api/login"
    Then The response status code should be 200
    And The response should contain a valid token

  Scenario: Get All Object
    When User sends GET request to "/webhook/api/objects"
    Then The response status code should be 200
    And The object list should not be empty

  Scenario: Add new object
  When User adds a new object with the following details:
    | name          | year | price | cpu_model | hard_disk_size | capacity | screen_size | color |
    | Asus ROG MUX  | 2024 | 2000  | Ryzen 7   | 1TB            | 8 CPU    | 17 Inch     | Black |
  Then The response status code should be 200
  And The response should contain object id

   Scenario: Update existing object
   When User updates the object with the following details:
      | name         | year | price | cpu_model | hard_disk_size | capacity | screen_size | color |
      | Asus ROG FX  | 2025 | 2500  | Ryzen 9   | 2TB            | 12 CPU   | 18 Inch     | Silver |
   Then The response status code should be 200

    Scenario: Delete object
    When User deletes the object
    Then The response status code should be 200