import React, { useState } from 'react'

interface Props<T> {
    initialValues: T
}

export const useForm = <T extends Record<string, any>>({ initialValues }: Props<T>) => {
    const [formData, setFormData] = useState<T>(initialValues)
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        setFormData((prev) => ({ ...prev, [name]: value }))
    }
    const setFormValues = (values: Partial<T>) => {
        setFormData((prev) => ({ ...prev, ...values }))
    }

    const updateFormData = (updater: (prev: T) => T) => {
        setFormData((prev) => updater(prev))
    }

    const resetForm = () => {
        setFormData(initialValues)
        setError(null)
    }

    return {
        formData,
        setFormData,
        handleChange,
        setFormValues,
        resetForm,
        updateFormData,
        error,
        setError,
        loading,
        setLoading
    }
}
