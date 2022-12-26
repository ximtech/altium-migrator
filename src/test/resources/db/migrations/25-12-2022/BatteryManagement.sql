--liquibase formatted sql
--changeset Stanislav_Vodolagin:1

CREATE TABLE IF NOT EXISTS "BatteryManagement" (
  "id" bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  "Description" varchar(255) NULL,
  "Part Number" varchar(126) NULL,
  "ComponentLink2URL" varchar(255) NULL,
  "Device Package" varchar(126) NULL,
  "Supplier Device Package" varchar(126) NULL,
  "Function" varchar(126) NULL,
  "Package / Case" varchar(126) NULL,
  "Library Ref" varchar(126) NULL,
  "Supplier Part Number 1" varchar(126) NULL,
  "Lifecycle Status" varchar(126) NULL,
  "Battery Chemistry" varchar(126) NULL,
  "Manufacturer" varchar(126) NULL,
  "LastUpdated" varchar(255) NULL,
  "Minimum Order" varchar(16) NULL,
  "Comment" varchar(126) NULL,
  "Operating Temperature" varchar(126) NULL,
  "ComponentLink1URL" varchar(255) NULL,
  "Footprint Ref" varchar(126) NULL,
  "Number of Cells" varchar(16) NULL,
  "ComponentLink1Description" varchar(126) NULL,
  "Supplier 1" varchar(126) NULL,
  "Mounting Type" varchar(126) NULL,
  "Series" varchar(126) NULL,
  "Price" varchar(64) NULL,
  "Library Path" varchar(255) NULL,
  "Packaging" varchar(126) NULL,
  "Part Status" varchar(126) NULL,
  "ComponentLink2Description" varchar(126) NULL,
  "Footprint Path" varchar(255) NULL,
  "Interface" varchar(126) NULL,
  PRIMARY KEY ("id")
);

CREATE INDEX "54f9b250-0b35-4e6e-9be4-b6e795dbc842" ON "BatteryManagement"("Mounting Type");

INSERT INTO "BatteryManagement" (
  "Battery Chemistry",
  "Comment",
  "ComponentLink1Description",
  "ComponentLink1URL",
  "ComponentLink2Description",
  "ComponentLink2URL",
  "Description",
  "Device Package",
  "Footprint Path",
  "Footprint Ref",
  "Function",
  "Interface",
  "LastUpdated",
  "Lifecycle Status",
  "Manufacturer",
  "Part Number",
  "Minimum Order",
  "Mounting Type",
  "Number of Cells",
  "Operating Temperature",
  "Package / Case",
  "Packaging",
  "Part Status",
  "Price",
  "Series",
  "Supplier 1",
  "Supplier Part Number 1",
  "Supplier Device Package",
  "Library Path",
  "Library Ref"
)
VALUES (
  'Lithium-Ion', 
  '=Part Number', 
  'Datasheet', 
  'http://www.ti.com/general/docs/suppproductinfo.tsp?distId=10&gotoUrl=http%3A%2F%2Fwww.ti.com%2Flit%2Fgpn%2Fbq27542-g1', 
  'DigiKey Link', 
  'http://digikey.com/product-detail/en/texas-instruments/BQ27542DRZR-G1/296-44346-1-ND/6110619', 
  'IC BATT FUEL GAUGE LI-ION 12SON', 
  '12-VFDFN Exposed Pad', 
  'footprints/Leadless - SON/PCB - LEADLESS - SON - TI SON-12 DRZ.PCBLIB', 
  'TI SON-12 DRZ', 
  'Battery Monitor', 
  'HDQ, I²C', 
  '2020-03-30T16:23:26.660', 
  'Active', 
  'Texas Instruments', 
  'BQ27542DRZR-G1', 
  '1', 
  'Surface Mount', 
  '1', 
  '-40°C ~ 85°C (TA)', 
  '12-VFDFN Exposed Pad', 
  'TapeAndReel', 
  'Active', 
  '1.28', 
  'Impedance Track™', 
  'DigiKey', 
  '296-44346-1-ND', 
  '12-SON (2.5x4)', 
  'symbols/Battery Management/SCH - BATTERY MANAGEMENT - TI BQ27542DRZR-G1.SCHLIB', 
  'TI BQ27542DRZR-G1'
);

