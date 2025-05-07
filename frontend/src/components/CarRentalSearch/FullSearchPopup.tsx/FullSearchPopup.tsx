import React, { useRef, useState } from "react";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { Input } from "@/components/ui/input";
import { cn } from "@/lib/utils";

// Define the shape of the form data
interface FilterForm {
  carClass: string;
  gearboxType: string;
  fuelType: string;
  priceMin: number;
  priceMax: number;
  brand: string;
}

// Options for our popover selects
const OPTIONS = {
  carClass: ["ECONOMY", "BUSINESS", "LUXURY"],
  gearboxType: ["AUTO", "MANUAL"],
  fuelType: ["PETROL", "DIESEL", "ELECTRIC"],
};

// Generic PopoverSelect component using uncontrolled form (hidden input)
interface PopoverSelectProps {
  label: string;
  name: keyof FilterForm;
  options: string[];
  selected: string;
  onSelect: (value: string) => void;
}

function PopoverSelect({ label, name, options, selected, onSelect }: PopoverSelectProps) {
  return (
    <div className="space-y-1">
      <label className="block text-sm font-medium text-gray-700">{label}</label>
      {/* Hidden input holds the selected value for form submission */}
      <input type="hidden" name={name} value={selected} />
      <Popover>
        <PopoverTrigger asChild>
          <Button
            type="button"
            variant="outline"
            className="w-full justify-start text-left"
          >
            {selected || `Select ${label.toLowerCase()}`}
          </Button>
        </PopoverTrigger>
        <PopoverContent className="w-full p-2 space-y-1 cursor-pointer">
          {options.map((option) => (
            <Button
              key={option}
              type="button"
              variant="ghost"
              className={cn(
                "w-full justify-start",
                selected === option && "bg-muted"
              )}
              onClick={() => onSelect(option)}
            >
              {option}
            </Button>
          ))}
        </PopoverContent>
      </Popover>
    </div>
  );
}

// Main component
export function FullSearchPopup() {
  const [open, setOpen] = useState(false);
  const formRef = useRef<HTMLFormElement>(null);

  // Local state for popover selections
  const [carClass, setCarClass] = useState<string>("");
  const [gearboxType, setGearboxType] = useState<string>("");
  const [fuelType, setFuelType] = useState<string>("");
  const [brand, setBrand] = useState<string>("");

  // Example brand options; could be fetched
  const [brands] = useState<string[]>(["BMW", "Merc", "XD"]);

  // Handle form submission: use FormData to gather all inputs
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!formRef.current) return;
    const data = new FormData(formRef.current);
    const values = Object.fromEntries(data.entries());
    const parsed: FilterForm = {
      carClass: String(values.carClass || ""),
      gearboxType: String(values.gearboxType || ""),
      fuelType: String(values.fuelType || ""),
      priceMin: Number(values.priceMin || 0),
      priceMax: Number(values.priceMax || 0),
      brand: String(values.brand || ""),
    };
    console.log("Form submitted: ", parsed);
    setOpen(false);
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button variant="outline">More options</Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-md">
        <h2 className="text-lg font-semibold">Additional options</h2>
        <p className="text-sm text-muted-foreground mb-4">
          Select a car to rent!
        </p>
        <form ref={formRef} onSubmit={handleSubmit} className="space-y-4">
          <PopoverSelect
            label="Car Class"
            name="carClass"
            options={OPTIONS.carClass}
            selected={carClass}
            onSelect={setCarClass}
          />
          <PopoverSelect
            label="Gearbox"
            name="gearboxType"
            options={OPTIONS.gearboxType}
            selected={gearboxType}
            onSelect={setGearboxType}
          />
          <PopoverSelect
            label="Fuel Type"
            name="fuelType"
            options={OPTIONS.fuelType}
            selected={fuelType}
            onSelect={setFuelType}
          />
          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-1">
              <label className="block text-sm font-medium text-gray-700">
                Min Price
              </label>
              <Input
                type="number"
                name="priceMin"
                defaultValue={0}
                min={0}
              />
            </div>
            <div className="space-y-1">
              <label className="block text-sm font-medium text-gray-700">
                Max Price
              </label>
              <Input
                type="number"
                name="priceMax"
                defaultValue={1000}
                min={0}
              />
            </div>
          </div>
          <PopoverSelect
            label="Brand"
            name="brand"
            options={brands}
            selected={brand}
            onSelect={setBrand}
          />
          <div className="text-right">
            <Button type="submit">Submit</Button>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
}