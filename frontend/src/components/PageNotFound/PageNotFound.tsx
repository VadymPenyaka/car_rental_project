import React from "react";
import { Button } from "../ui/button";
import { Card, CardContent, CardHeader, CardTitle, CardFooter } from "../ui/card";
import { Link } from "react-router";

export const PageNotFound: React.FC = () => {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 text-gray-800 px-4">
      <Card className="w-full max-w-md text-center">
        <CardHeader>
          <CardTitle className="text-6xl font-bold text-orange-500">404</CardTitle>
        </CardHeader>
        <CardContent>
          <h2 className="text-xl font-semibold mb-4">
            <code>Page Not Found</code>
          </h2>
          <p className="text-gray-600">
            Ми не знайшли відповідної сторінки за вашим посиланням. <br />
            Можливо ми видалили застарілий вміст, або ви вписали неправильний адрес.
          </p>
        </CardContent>
        <CardFooter>
          <Button asChild variant="outline" className="text-orange-500 border-orange-500 hover:bg-orange-100">
            <Link to="/">Повернутися на головну</Link>
          </Button>
        </CardFooter>
      </Card>
    </div>
  );
};
