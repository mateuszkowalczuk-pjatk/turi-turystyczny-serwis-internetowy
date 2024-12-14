import { useTranslation } from 'react-i18next'
import PersonalPart from '../../../components/Personal/PersonalPart'
import PersonalPanel from '../../../components/Personal/PersonalPanel'
import PersonalLabel from '../../../components/Personal/PersonalLabel'
import PersonalInput from '../../../components/Personal/PersonalInput'
import PersonalGender from '../../../components/Personal/PersonalGender'
import styles from './ProfilePersonalPage.module.css'
import ProfileButton from '../../../components/Profile/ProfileButton'
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { accountService } from '../../../services/accountService.ts'
import { addressService } from '../../../services/addressService.ts'
import { notPersonalization } from '../../../store/slices/personal.ts'
import { RootState } from '../../../store/store.ts'
import { Gender } from '../../../types'

interface FormData {
    firstName: string
    lastName: string
    birthDay: number | null
    birthMonth: number | null
    birthYear: number | null
    gender: Gender | null
    phoneNumber: string
    address: {
        addressId: number
        country: string
        city: string
        zipCode: string
        street: string
        buildingNumber: string
        apartmentNumber: number
    }
}

const ProfilePersonalPage = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated)
    const [formData, setFormData] = useState<FormData>({
        firstName: '',
        lastName: '',
        birthDay: null,
        birthMonth: null,
        birthYear: null,
        gender: null,
        phoneNumber: '',
        address: {
            addressId: 0,
            country: '',
            city: '',
            zipCode: '',
            street: '',
            buildingNumber: '',
            apartmentNumber: 0
        }
    })
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        if (!isAuthenticated) {
            navigate('/')
        }

        const fetchData = async () => {
            setLoading(true)
            try {
                const accountResponse = await accountService.getById()
                if (accountResponse.status === 200) {
                    const accountData = await accountResponse.json()

                    const { firstName, lastName, birthDate, gender, phoneNumber, addressId } = accountData

                    const [year, month, day] = birthDate

                    const addressResponse = await addressService.getById(addressId)
                    if (addressResponse.status === 200) {
                        const addressData = await addressResponse.json()

                        setFormData({
                            firstName,
                            lastName,
                            birthDay: day,
                            birthMonth: month,
                            birthYear: year,
                            gender: gender === 'MALE' ? Gender.MALE : Gender.FEMALE,
                            phoneNumber,
                            address: {
                                addressId: addressData.addressId,
                                country: addressData.country,
                                city: addressData.city,
                                zipCode: addressData.zipCode,
                                street: addressData.street,
                                buildingNumber: addressData.buildingNumber,
                                apartmentNumber: addressData.apartmentNumber
                            }
                        })
                    } else {
                        setError(t('signup-personal.error-default-personal'))
                    }
                } else {
                    setError(t('signup-personal.error-default-personal'))
                }
            } catch (error) {
                setError(t('signup-personal.error-default-personal'))
            } finally {
                setLoading(false)
            }
        }

        fetchData().catch((error) => error)
    }, [isAuthenticated, navigate, t])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        const parsedValue = ['birthDay', 'birthMonth', 'birthYear', 'apartmentNumber'].includes(name)
            ? value !== ''
                ? Number(value)
                : null
            : value === ''
              ? null
              : value

        setFormData((prevData) => {
            if (name.startsWith('address.')) {
                const addressField = name.split('.')[1]
                return {
                    ...prevData,
                    address: {
                        ...prevData.address,
                        [addressField]: parsedValue
                    }
                }
            } else {
                return {
                    ...prevData,
                    [name]: parsedValue
                }
            }
        })
    }

    const handleGenderChange = (gender: Gender) => {
        setFormData((prevData) => ({
            ...prevData,
            gender: gender
        }))
    }

    const handleUpdateAccount = async () => {
        setLoading(true)
        setError(null)

        if (
            !formData.firstName ||
            !formData.lastName ||
            formData.birthDay === null ||
            formData.birthMonth === null ||
            formData.birthYear === null ||
            formData.gender === null ||
            !formData.phoneNumber ||
            !formData.address.country ||
            !formData.address.city ||
            !formData.address.zipCode ||
            !formData.address.street ||
            !formData.address.buildingNumber
        ) {
            setError(t('signup-personal.error-missing-fields'))
            setLoading(false)
            return
        }

        if (
            formData.birthDay < 1 ||
            formData.birthDay > 31 ||
            formData.birthMonth < 1 ||
            formData.birthMonth > 12 ||
            formData.birthYear < 1900 ||
            formData.birthYear > new Date().getFullYear()
        ) {
            setError(t('signup-personal.error-invalid-birthdate'))
            setLoading(false)
            return
        }

        const zipCodeRegex = /^\d{2}-\d{3}$/
        if (!zipCodeRegex.test(formData.address.zipCode)) {
            setError(t('signup-personal.error-invalid-zipcode'))
            setLoading(false)
            return
        }

        const phoneRegex = /^\d{9,15}$/
        if (!phoneRegex.test(formData.phoneNumber)) {
            setError(t('signup-personal.error-invalid-phone-number'))
            setLoading(false)
            return
        }

        if (
            formData.address.apartmentNumber &&
            (isNaN(Number(formData.address.apartmentNumber)) || formData.address.apartmentNumber < 0)
        ) {
            setError(t('signup-personal.error-invalid-apartment-number'))
            setLoading(false)
            return
        }

        try {
            const phoneNumberCheckResponse = await accountService.isPhoneNumberExists(formData.phoneNumber)
            if (phoneNumberCheckResponse.status === 200) {
                const result = await phoneNumberCheckResponse.json()
                if (result) {
                    setError(t('signup-personal.error-phone-number-exists'))
                    setLoading(false)
                    return
                }
            } else {
                setError(t('signup-personal.error-general'))
                setLoading(false)
                return
            }

            const addressCheckResponse = await accountService.isAddressExists(
                formData.address.country,
                formData.address.city,
                formData.address.zipCode,
                formData.address.street,
                formData.address.buildingNumber,
                formData.address.apartmentNumber ? formData.address.apartmentNumber.toString() : null
            )
            if (addressCheckResponse.status === 200) {
                const result = await addressCheckResponse.json()
                if (!result) {
                    const addressCreateResponse = await addressService.create({
                        country: formData.address.country,
                        city: formData.address.city,
                        zipCode: formData.address.zipCode,
                        street: formData.address.street,
                        buildingNumber: formData.address.buildingNumber,
                        apartmentNumber:
                            formData.address.apartmentNumber === null ? undefined : formData.address.apartmentNumber
                    })
                    if (addressCreateResponse.status !== 200) {
                        setError(t('signup-personal.error-general'))
                        setLoading(false)
                        return
                    }
                    const address = await addressCreateResponse.json()
                    formData.address.addressId = address.addressId
                }
            } else {
                setError(t('signup-personal.error-general'))
                setLoading(false)
                return
            }

            const birthDate = `${formData.birthYear.toString().padStart(4, '0')}-${formData.birthMonth.toString().padStart(2, '0')}-${formData.birthDay.toString().padStart(2, '0')}`

            const response = await accountService.update({
                addressId: formData.address.addressId,
                firstName: formData.firstName,
                lastName: formData.lastName,
                birthDate: new Date(birthDate),
                phoneNumber: formData.phoneNumber,
                gender: formData.gender
            })
            if (response.status === 200) {
                dispatch(notPersonalization())
                navigate('/')
            } else {
                setError(t('signup-personal.error-general'))
            }
        } catch (error) {
            setError(t('signup-personal.error-general'))
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className={styles.page}>
            <PersonalPart
                firstPanel={
                    <PersonalPanel
                        label={<PersonalLabel text={t('signup-personal.name-surname')} />}
                        firstInput={
                            <PersonalInput
                                type={'text'}
                                name={'firstName'}
                                placeholder={t('signup-personal.name')}
                                value={formData.firstName}
                                onChange={handleChange}
                                minLength={3}
                                maxLength={50}
                                required={true}
                                disabled={loading}
                            />
                        }
                        secondInput={
                            <PersonalInput
                                type={'text'}
                                name={'lastName'}
                                placeholder={t('signup-personal.surname')}
                                value={formData.lastName}
                                onChange={handleChange}
                                minLength={3}
                                maxLength={50}
                                required={true}
                                disabled={loading}
                            />
                        }
                    />
                }
                secondPanel={
                    <PersonalPanel
                        label={<PersonalLabel text={t('signup-personal.birthdate')} />}
                        firstInput={
                            <PersonalInput
                                type={'number'}
                                name={'birthDay'}
                                placeholder={t('signup-personal.day')}
                                value={formData.birthDay === null ? undefined : formData.birthDay}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        secondInput={
                            <PersonalInput
                                type={'number'}
                                name={'birthMonth'}
                                placeholder={t('signup-personal.month')}
                                value={formData.birthMonth === null ? undefined : formData.birthMonth}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        thirdInput={
                            <PersonalInput
                                type={'number'}
                                name={'birthYear'}
                                placeholder={t('signup-personal.year')}
                                value={formData.birthYear === null ? undefined : formData.birthYear}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                    />
                }
                option={
                    <PersonalPanel
                        label={<PersonalLabel text={t('signup-personal.gender')} />}
                        firstInput={
                            <PersonalGender
                                gender={formData.gender}
                                handleGenderChange={handleGenderChange}
                            />
                        }
                    />
                }
            />
            <PersonalPart
                firstPanel={
                    <PersonalPanel
                        label={<PersonalLabel text={t('signup-personal.phone-number')} />}
                        firstInput={
                            <PersonalInput
                                type={'text'}
                                name={'phoneNumber'}
                                placeholder={t('signup-personal.phone-number')}
                                value={formData.phoneNumber}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                    />
                }
                secondPanel={
                    <PersonalPanel
                        label={<PersonalLabel text={t('signup-personal.address')} />}
                        firstInput={
                            <PersonalInput
                                type={'text'}
                                name={'address.country'}
                                placeholder={t('signup-personal.country')}
                                value={formData.address.country || ''}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        secondInput={
                            <PersonalInput
                                type={'text'}
                                name={'address.city'}
                                placeholder={t('signup-personal.city')}
                                value={formData.address.city || ''}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        thirdInput={
                            <PersonalInput
                                type={'text'}
                                name={'address.zipCode'}
                                placeholder={t('signup-personal.zipcode')}
                                value={formData.address.zipCode || ''}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        fourthInput={
                            <PersonalInput
                                type={'text'}
                                name={'address.street'}
                                placeholder={t('signup-personal.street')}
                                value={formData.address.street || ''}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        fifthInput={
                            <PersonalInput
                                type={'text'}
                                name={'address.buildingNumber'}
                                placeholder={t('signup-personal.building')}
                                value={formData.address.buildingNumber || ''}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        sixthInput={
                            <PersonalInput
                                type={'number'}
                                name={'address.apartmentNumber'}
                                placeholder={t('signup-personal.apartment')}
                                value={
                                    formData.address.apartmentNumber === null
                                        ? undefined
                                        : formData.address.apartmentNumber
                                }
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                    />
                }
                option={
                    <ProfileButton
                        handleSave={handleUpdateAccount}
                        error={error}
                    />
                }
            />
        </div>
    )
}

export default ProfilePersonalPage
