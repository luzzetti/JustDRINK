ALTER TABLE public.restaurants
    DISABLE TRIGGER ALL;

INSERT INTO public.restaurants (id, addressdisplayname, addresslatitude, addresslongitude, enabled,
                                logo_path, name, owner_email, owner_id, owner_username)
VALUES ('49849cf3-5ef8-491b-ab6c-5d35cb007e38', 'Via Orazio Coccanari, 25, 00019, Tivoli',
        41.9585226, 12.7741917, false, null, 'a Casa di Stefano', 'justdrink.admin@gmail.com',
        'f71d7aa3-09dc-44c7-945c-5680fc7344bd', 'justdrink.admin@gmail.com');

INSERT INTO public.restaurants (id, addressdisplayname, addresslatitude, addresslongitude, enabled,
                                logo_path, name, owner_email, owner_id, owner_username)
VALUES ('ce3e7069-09dc-448b-8a33-0a7c503576ab', 'Via ildebrando vivanti, 188, roma', 41.8044097,
        12.4416371, false, null, 'a Casa di Christian', 'justdrink.admin@gmail.com',
        'f71d7aa3-09dc-44c7-945c-5680fc7344bd', 'justdrink.admin@gmail.com');

ALTER TABLE public.restaurants
    enable TRIGGER ALL;
