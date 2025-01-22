import React, { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import PersonalPart from '../../../Shared/Personal/PersonalPart'
import PersonalPanel from '../../../Shared/Personal/PersonalPanel'
import Label from '../../../Shared/Controls/Label'
import PersonalInput from '../../../Shared/Personal/PersonalInput'
import PersonalGender from '../../../Shared/Personal/PersonalGender'
import AuthPersonalButtons from '../AuthPersonalButtons'
import AuthPersonalSkipOverlay from '../AuthPersonalSkip/AuthPersonalSkipOverlay'
import { Gender } from '../../../../types'
import { notPersonalization } from '../../../../store/slices/personal.ts'
import { accountService } from '../../../../services/accountService.ts'
import { addressService } from '../../../../services/addressService.ts'
import styles from './AuthPersonalContent.module.css'
import { useAppDispatch } from '../../../../hooks/useAppDispatch.ts'

interface FormData {
    firstName: string
    lastName: string
    birthDay: number | null
    birthMonth: number | null
    birthYear: number | null
    gender: Gender | null
    phoneNumber: string
    address: {
        country: string
        city: string
        zipCode: string
        street: string
        buildingNumber: string
        apartmentNumber: number | null
    }
}

const AuthPersonalContent = () => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const dispatch = useAppDispatch()
    const [isPersonalSkip, setIsPersonalSkip] = useState(false)
    const [formData, setFormData] = useState<FormData>({
        firstName: '',
        lastName: '',
        birthDay: null,
        birthMonth: null,
        birthYear: null,
        gender: null,
        phoneNumber: '',
        address: {
            country: '',
            city: '',
            zipCode: '',
            street: '',
            buildingNumber: '',
            apartmentNumber: null
        }
    })
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

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
                if (result) {
                    setError(t('signup-personal.error-address-exists'))
                    setLoading(false)
                    return
                }
            } else {
                setError(t('signup-personal.error-general'))
                setLoading(false)
                return
            }

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

            const birthDate = `${formData.birthYear.toString().padStart(4, '0')}-${formData.birthMonth.toString().padStart(2, '0')}-${formData.birthDay.toString().padStart(2, '0')}`

            const response = await accountService.update({
                addressId: address.addressId,
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
        <>
            <div className={`${styles.content} ${isPersonalSkip ? styles.background : ''}`}>
                <PersonalPart
                    firstPanel={
                        <PersonalPanel
                            label={<Label text={t('signup-personal.name-surname')} />}
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
                            label={<Label text={t('signup-personal.birthdate')} />}
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
                            label={<Label text={t('signup-personal.gender')} />}
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
                            label={<Label text={t('signup-personal.phone-number')} />}
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
                            label={<Label text={t('signup-personal.address')} />}
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
                        <AuthPersonalButtons
                            skipOnClick={() => setIsPersonalSkip(true)}
                            handleUpdateAccount={handleUpdateAccount}
                            error={error}
                        />
                    }
                />
            </div>
            {isPersonalSkip && <AuthPersonalSkipOverlay onClick={() => setIsPersonalSkip(false)} />}
        </>
    )
}

export default AuthPersonalContent
