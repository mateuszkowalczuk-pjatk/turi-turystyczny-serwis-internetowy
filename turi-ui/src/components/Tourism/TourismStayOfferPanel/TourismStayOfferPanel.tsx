import React, { useEffect, useState } from 'react'
import { useId } from '../../../hooks/shared/useId.ts'
import { handle } from '../../../utils/handle.ts'
import { useForm } from '../../../hooks/shared/useForm.ts'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { handleFormError } from '../../../utils/handleFormError.ts'
import Label from '../../Shared/Controls/Label'
import Input from '../../Shared/Controls/Input'
import ImageBanner from '../../Shared/Image/ImageBanner'
import PersonalPanel from '../../Shared/Personal/PersonalPanel'
import TourismOfferDetails from '../TourismOfferDetails'
import TourismOfferButtons from '../TourismOfferButtons'
import TourismStayInformations from '../TourismStayInformations'
import { Image, ImageMode } from '../../../types/image.ts'
import { StayDto, StayInformation } from '../../../types/stay.ts'
import { stayService } from '../../../services/stayService.ts'
import { imageService } from '../../../services/imageService.ts'
import styles from './TourismStayOfferPanel.module.css'

interface Props {
    touristicPlaceId: number
    modify?: boolean
}

interface FormData {
    name: string
    description: string
    price: number | null
    peopleNumber: number | null
    dateFrom: Date | null
    dateTo: Date | null
}

const TourismStayOfferPanel = ({ touristicPlaceId, modify = false }: Props) => {
    const { t, navigate, location } = useHooks()
    const { stayId = null } = location.state || {}
    const [images, setImages] = useState<Image[]>([])
    const [files, setFiles] = useState<File[]>([])
    const [loading, setLoading] = useState(false)
    const [stayInformations, setStayInformations] = useState<StayInformation[]>([])
    const { formData, error, setError, handleChange, setFormValues } = useForm<FormData>({
        initialValues: {
            name: '',
            description: '',
            price: null,
            peopleNumber: null,
            dateFrom: null,
            dateTo: null
        }
    })

    useId(stayId, '/tourism', modify)

    useEffect(() => {
        const fetchData = async () => {
            const stayResponse = await stayService.getById(stayId)
            if (stayResponse.status === 200) {
                const stayData: StayDto = await stayResponse.json()
                setFormValues({
                    name: stayData.name,
                    description: stayData.description,
                    price: stayData.price,
                    peopleNumber: stayData.peopleNumber,
                    dateFrom: convertToDate(stayData.dateFrom),
                    dateTo: convertToDate(stayData.dateTo)
                })
                setStayInformations(stayData.stayInformations)
                const imagesResponse = await imageService.getAllByStayId(stayId)
                if (imagesResponse.status === 200) {
                    const imagesData = await imagesResponse.json()
                    setImages(imagesData)
                }
            }
        }

        const convertToDate = (dateArray: any): Date | null => {
            if (!dateArray) return null
            const [year, month, day] = dateArray
            return new Date(Date.UTC(year, month - 1, day))
        }

        stayId && fetchData().catch((error) => error)
    }, [])

    const handleSave = async (e: React.FormEvent) => {
        handle(e, setLoading, setError)

        if (
            formData.dateFrom &&
            formData.dateTo &&
            new Date(formData.dateFrom).getTime() >= new Date(formData.dateTo).getTime()
        )
            return handleFormError(setError, setLoading, t('tourism.touristic-place-stay-offer-create-error'))

        if (formData.price === null || formData.peopleNumber === null || formData.dateFrom === null)
            return handleFormError(setError, setLoading, 'tourism.touristic-place-stay-offer-create-fields')

        if (modify) {
            const stayResponse = await stayService.update(stayId, {
                touristicPlaceId: touristicPlaceId,
                name: formData.name,
                description: formData.description,
                price: formData.price,
                peopleNumber: formData.peopleNumber,
                dateFrom: formData.dateFrom,
                dateTo: formData.dateTo || undefined
            })
            setLoading(false)
            if (stayResponse.status !== 200)
                return handleFormError(setError, setLoading, 'tourism.touristic-place-stay-offer-modify-error')
        } else {
            const stayResponse = await stayService.create({
                touristicPlaceId: touristicPlaceId,
                name: formData.name,
                description: formData.description,
                price: formData.price,
                peopleNumber: formData.peopleNumber,
                dateFrom: formData.dateFrom,
                dateTo: formData.dateTo || undefined,
                stayInformations: stayInformations
            })
            if (stayResponse.status === 200) {
                const stayData = await stayResponse.json()
                files.forEach((file) => {
                    imageService.upload(file, ImageMode.STAY, stayData.stayId)
                })
                navigate('/tourism')
            } else return handleFormError(setError, setLoading, 'tourism.touristic-place-stay-offer-create-error')
        }
    }

    return (
        <form
            className={styles.form}
            onSubmit={handleSave}
        >
            <div className={styles.panel}>
                {!stayId && (
                    <ImageBanner
                        files={files}
                        uploadFile={(file: File) => setFiles((prevFiles) => [...prevFiles, file])}
                        removeFile={(file: File) =>
                            setFiles((prevFiles) => prevFiles.filter((prevFile) => prevFile !== file))
                        }
                    />
                )}
                {stayId && (
                    <ImageBanner
                        images={images}
                        uploadImage={(image: Image) => setImages((prevImages) => [...prevImages, image])}
                        mode={ImageMode.STAY}
                        id={stayId}
                        removeImage={(imageId: number) =>
                            setImages((prevImage) => prevImage.filter((image) => image.imageId !== imageId))
                        }
                    />
                )}
                <div className={styles.content}>
                    <TourismOfferDetails
                        firstPanel={
                            <PersonalPanel
                                label={<Label text={t('tourism.touristic-place-stay-offer-name-and-description')} />}
                                firstInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.touristic-place-stay-offer-name')}
                                        value={formData.name}
                                        minLength={5}
                                        maxLength={50}
                                        onChange={handleChange}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <Input
                                        type={'text'}
                                        name={'description'}
                                        placeholder={t('tourism.touristic-place-stay-offer-description')}
                                        value={formData.description}
                                        minLength={5}
                                        maxLength={255}
                                        onChange={handleChange}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                        secondPanel={
                            <PersonalPanel
                                label={<Label text={t('tourism.touristic-place-stay-offer-information')} />}
                                firstInput={
                                    <Input
                                        type={'number'}
                                        name={'price'}
                                        placeholder={t('tourism.touristic-place-stay-offer-price')}
                                        value={formData.price}
                                        onChange={handleChange}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <Input
                                        type={'number'}
                                        name={'peopleNumber'}
                                        placeholder={t('tourism.touristic-place-stay-offer-people-number')}
                                        value={formData.peopleNumber}
                                        onChange={handleChange}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                        thirdPanel={
                            <PersonalPanel
                                label={<Label text={t('tourism.touristic-place-stay-offer-availability-range')} />}
                                firstInput={
                                    <Input
                                        type={'date'}
                                        name={'dateFrom'}
                                        placeholder={t('tourism.touristic-place-stay-offer-availability-range-from')}
                                        value={formData.dateFrom}
                                        onChange={handleChange}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <Input
                                        type={'date'}
                                        name={'dateTo'}
                                        placeholder={t('tourism.touristic-place-stay-offer-availability-range-to')}
                                        value={formData.dateTo}
                                        onChange={handleChange}
                                        required={false}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                    />
                    {!modify && (
                        <TourismStayInformations
                            stayInformations={stayInformations}
                            createStayInformation={(stayInformation: StayInformation) =>
                                setStayInformations((prevStayInformations) => [
                                    ...prevStayInformations,
                                    stayInformation
                                ])
                            }
                            removeStayInformation={(information: string) =>
                                setStayInformations((prevStayInformations) =>
                                    prevStayInformations.filter(
                                        (stayInformation) => stayInformation.information !== information
                                    )
                                )
                            }
                        />
                    )}
                    {modify && (
                        <TourismStayInformations
                            stayInformations={stayInformations}
                            createStayInformation={(stayInformation: StayInformation) =>
                                setStayInformations((prevStayInformations) => [
                                    ...prevStayInformations,
                                    stayInformation
                                ])
                            }
                            removeStayInformation={(information: string) =>
                                setStayInformations((prevStayInformations) =>
                                    prevStayInformations.filter(
                                        (stayInformation) => stayInformation.information !== information
                                    )
                                )
                            }
                            stayId={stayId}
                            modify={modify}
                        />
                    )}
                </div>
                <TourismOfferButtons
                    modify={modify || false}
                    id={stayId}
                    mode={'stay'}
                    error={error}
                />
            </div>
        </form>
    )
}

export default TourismStayOfferPanel
