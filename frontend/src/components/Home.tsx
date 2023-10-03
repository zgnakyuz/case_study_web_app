import React, { useState, useEffect } from "react";

import { getAllProducts, getSelectedProduct } from "../services/user.service";
import { Product } from "../types/product";
import IUser from '../types/user.type';
import * as AuthService from "../services/auth.service";
import Swal from 'sweetalert2';
import { title } from "process";

const Home: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([])
  const [loading, setLoading] = useState(true);
  const [currentUser, setCurrentUser] = useState<IUser | undefined>(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
    }

    getAllProducts().then(
      (response) => {
        setProducts(response.data);
        setLoading(false);
      },
      (error) => {
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();
        setLoading(false);

        setProducts(_content);
      }
    );
  }, []);

  const handleSelectProduct = async (productId: number) => {
    getSelectedProduct(productId).then(
      (response) => {
        if (response.data) {
          const selectedProduct = response.data;
          console.log(selectedProduct);
          if (currentUser) {
            if (currentUser.money >= selectedProduct.price) {
              Swal.fire({
                title: 'Confirmation',
                text: 'Do you want to continue with this purchase?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Yes',
                cancelButtonText: 'No',
              }).then((result) => {
                if (result.isConfirmed) {
                  // User confirmed, you can perform your backend endpoint call or any other action here.
                  console.log('User confirmed the purchase.');
                } else {
                  console.log('User canceled the purchase.');
                }
              });
            } else {
              Swal.fire({title: 'User cannot afford the selected product.'});
            }
          } else {
            Swal.fire({title: 'User error!'});
          }
        }
        setLoading(false);
      },
      (error) => {
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();
        setLoading(false);
      }
    );
  };

  return (
    <div className="container">
      {loading ? (
        <p>Loading...</p>
      ) : (
        <div className="product-grid">
          {products.map((product) => (
            <div key={product.id} className="product">
              <div className="product-info">
                <h4>{product.name}</h4>
                <p>Price: ${product.price}</p>
                <p>{product.count} in stock</p>
              </div>
              {currentUser && currentUser.roles?.includes("ROLE_USER") && (
                <button onClick={() => handleSelectProduct(product.id)}>
                  Select
                </button>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Home;
