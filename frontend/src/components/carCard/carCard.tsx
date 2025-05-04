import { Card, CardHeader, CardTitle, CardContent, CardFooter } from "../ui/card"
import { Button } from "../ui/button"
import { Tabs, TabsList, TabsTrigger, TabsContent } from "../ui/tabs"
import { Table, TableHeader, TableRow, TableHead, TableBody, TableCell } from "../ui/table"
import { userCar } from "@/interfaces/userCar"

interface CarCardProps {
  userCar: userCar
}

export const CarCard: React.FC<CarCardProps> = ({ userCar }) => {
  const {
    id,
    modelName,
    brandName,
    numberOfSeats,
    fuelType,
    fuelConsumption,
    engineCapacity,
    gearboxType,
  } = userCar

  const {
    pledge,
    upToThreeDays,
    upToTenDays,
    upToMonth,
    moreThenMonth
  } = userCar.carPricing

  return (
    <Card className="w-full sm:w-auto max-w-sm mx-auto">
      <CardHeader>
        <img
          src={`https://carrental.fra1.digitaloceanspaces.com/cars/${id}_0.avif`}
          alt={brandName + " " + modelName}
          loading="lazy"
          className="w-full rounded-md object-cover"
        />
        <div className="flex gap-2 mt-2 overflow-x-auto">
          {[1, 2, 3, 4].map((_, idx) => {
            console.log(`https://carrental.fra1.digitaloceanspaces.com/cars/${id}_${idx}.avif`);
            
            return (
            <img
              key={idx}
              src={`https://carrental.fra1.digitaloceanspaces.com/cars/${id}_${idx}.avif`}
              alt={`thumb-${idx + 1}`}
              className="w-20 h-14 object-cover rounded-md border"
            />
          )})}
        </div>
      </CardHeader>

      <CardContent>
        <CardTitle className="text-xl font-semibold mb-2">
          {brandName} {modelName}
        </CardTitle>
        <div className="grid grid-cols-3 gap-4 text-sm text-gray-700">
          <div>{engineCapacity.toFixed(2)}L</div>
          <div>{fuelType}</div>
          <div>{gearboxType}</div>
          <div>{numberOfSeats} чол</div>
          <div>{fuelConsumption} л / 100 км</div>
        </div>

        <Tabs defaultValue="rental" className="mt-6">
          <TabsList className="w-full">
            <TabsTrigger value="rental" className="w-1/2">
              Прокат без водія
            </TabsTrigger>
            <TabsTrigger value="location" className="w-1/2">
              Адреса подачі
            </TabsTrigger>
          </TabsList>

          <TabsContent value="rental">
            <Table className="mt-4">
              <TableHeader>
                <TableRow>
                  <TableHead>Period</TableHead>
                  <TableHead>more than month</TableHead>
                  <TableHead>10-29 days</TableHead>
                  <TableHead>4-9 days</TableHead>
                  <TableHead>1-3 days</TableHead>
                  <TableHead>Pledge</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                <TableRow>
                  <TableCell>Price (with Taxes)</TableCell>
                  <TableCell>{moreThenMonth.toFixed(2)}$</TableCell>
                  <TableCell>{upToMonth.toFixed(2)}$</TableCell>
                  <TableCell>{upToTenDays.toFixed(2)}$</TableCell>
                  <TableCell>{upToThreeDays.toFixed(2)}$</TableCell>
                  <TableCell className="text-orange-500">{pledge.toFixed(2)}$</TableCell>
                </TableRow>
              </TableBody>
            </Table>

            <p className="text-xs mt-2 text-gray-500">
              * Ознайомитися з{" "}
              <span className="text-orange-500 underline">умовами оренди авто на добу</span>
              <br />
              ** Додаткове страхування доступне при оренді від 3-х діб
            </p>
          </TabsContent>

          <TabsContent value="location">
            <p className="text-gray-600 mt-4">Тут буде інформація про адресу подачі авто.</p>
          </TabsContent>
        </Tabs>
      </CardContent>

      <CardFooter className="justify-center">
        <Button className="bg-orange-500 hover:bg-orange-600 text-white">Замовити</Button>
      </CardFooter>
    </Card>
  )
}