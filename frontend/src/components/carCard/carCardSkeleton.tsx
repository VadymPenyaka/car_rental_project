import { Card, CardContent, CardFooter, CardHeader } from "../ui/card"
import { Skeleton } from "../ui/skeleton"
import { Tabs, TabsList, TabsTrigger, TabsContent } from "../ui/tabs"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "../ui/table"

export const CarCardSkeleton = () => {
  return (
    <Card className="w-full sm:w-auto max-w-sm mx-auto animate-pulse">
      <CardHeader>
        <Skeleton className="w-full h-48 rounded-md" />

        <div className="flex gap-2 mt-2 overflow-x-auto">
          {[1, 2, 3, 4].map((_, idx) => (
            <Skeleton key={idx} className="w-20 h-14 rounded-md" />
          ))}
        </div>
      </CardHeader>

      <CardContent>
        <Skeleton className="h-6 w-1/2 mb-4" />

        <div className="grid grid-cols-3 gap-4 text-sm text-gray-700 mb-6">
          {[...Array(5)].map((_, i) => (
            <Skeleton key={i} className="h-4 w-full" />
          ))}
        </div>

        <Tabs defaultValue="rental">
          <TabsList className="w-full">
            <TabsTrigger value="rental" className="w-1/2">Прокат без водія</TabsTrigger>
            <TabsTrigger value="location" className="w-1/2">Адреса подачі</TabsTrigger>
          </TabsList>

          <TabsContent value="rental" className="mt-4">
            <Table>
              <TableHeader>
                <TableRow>
                  {[...Array(6)].map((_, i) => (
                    <TableHead key={i}><Skeleton className="h-4 w-full" /></TableHead>
                  ))}
                </TableRow>
              </TableHeader>
              <TableBody>
                <TableRow>
                  {[...Array(6)].map((_, i) => (
                    <TableCell key={i}><Skeleton className="h-4 w-full" /></TableCell>
                  ))}
                </TableRow>
              </TableBody>
            </Table>
            <Skeleton className="h-4 mt-4 w-3/4" />
            <Skeleton className="h-4 mt-2 w-1/2" />
          </TabsContent>

          <TabsContent value="location">
            <Skeleton className="h-4 mt-4 w-2/3" />
          </TabsContent>
        </Tabs>
      </CardContent>

      <CardFooter className="justify-center">
        <Skeleton className="h-10 w-32 rounded-md" />
      </CardFooter>
    </Card>
  )
}