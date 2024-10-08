import { HamburgerMenuIcon } from "@radix-ui/react-icons"
import { Button } from "../ui/button"
 
export const NavigationButton = () => {
  return (
    <Button variant="outline" size="icon">
      <HamburgerMenuIcon className="h-4 w-4" />
    </Button>
  )
}